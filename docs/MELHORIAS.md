# Melhorias para 2026

Este documento analisa como o **api-nutricao** evoluiria se fosse construido hoje (2026), tres anos apos a versao original de 2023. Cada melhoria proposta considera o panorama atual de ferramentas, padroes e boas praticas do ecossistema Java/Spring.

> **Contexto**: O projeto original foi desenvolvido com Java 11, Spring Boot 2.7.18, MySQL 8, deploy manual em VM EC2 e testes com H2 em memoria. Embora solido e funcional, o ecossistema evoluiu significativamente desde entao.

---

## 1. Java 21 LTS e Spring Boot 3.x

| De (2023) | Para (2026) |
|---|---|
| Java 11 | Java 21 LTS |
| Spring Boot 2.7.18 | Spring Boot 3.3.x |
| Jakarta EE 8 (`javax.*`) | Jakarta EE 10 (`jakarta.*`) |

**Beneficios**:
- **Virtual Threads (Project Loom)**: Threads virtuais no Spring Boot 3.2+ permitem que o Tomcat processe milhares de requisicoes concorrentes com custo minimo de memoria — ideal para endpoints de dashboard e calculos nutricionais que fazem multiplas consultas ao banco.
- **Records**: Substituiriam todos os DTOs (`UsuarioResponse`, `AlimentoRequest`, etc.) com imutabilidade garantida e zero boilerplate:
  ```java
  public record AlimentoResponse(Long id, String nome, String categoria,
      BigDecimal calorias, BigDecimal proteina, BigDecimal carboidrato,
      BigDecimal gordura, BigDecimal fibra) {}
  ```
- **Pattern Matching**: Simplificaria o handler de excecoes e switch sobre enums (`TipoRefeicao`, `TipoObjetivo`).
- **Text Blocks**: Migrations SQL e documentacao Swagger mais legiveis com strings multilinha.

---

## 2. GraalVM Native Image

Compilar a aplicacao para binario nativo com **Spring Boot 3.x + GraalVM** traria:

- Startup de **< 0.1s** (vs ~5s da JVM tradicional)
- Consumo de memoria de **~50 MB** (vs ~300 MB da JVM)
- Ideal para serverless (AWS Lambda) e escalabilidade em Kubernetes

O trade-off principal e o tempo de build maior, mas para uma API de nutricao — com dominio estavel e poucas dependencias reflexivas — o perfil se encaixa bem.

---

## 3. Containerizacao Avancada

O projeto ja usa Docker, mas a abordagem de 2026 seria mais robusta:

### 3.1. Multi-stage Build Otimizado

```dockerfile
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine
RUN addgroup --system app && adduser --system app --ingroup app
USER app
COPY --from=build /build/target/*.jar app.jar
HEALTHCHECK --interval=30s CMD wget -qO- http://localhost:8080/actuator/health || exit 1
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 3.2. Docker Compose para Desenvolvimento

Adicionar **Redis** para cache, **Prometheus** para metricas e **Grafana** para dashboards no `docker-compose.yml` de desenvolvimento.

### 3.3. Orquestracao com Kubernetes

Substituir o deploy manual em EC2 por:
- **K3s** ou **MicroK8s** para ambientes menores
- **AWS EKS** ou **EKS Fargate** para producao
- **Helm charts** para empacotar a aplicacao com todos os recursos (ConfigMap, Secrets, Service, Ingress)

---

## 4. Banco de Dados e Cache

| Camada | Atual (2023) | Proposta (2026) |
|---|---|---|
| **Banco principal** | MySQL 8 | PostgreSQL 16 |
| **Cache** | Nenhum | Redis 7 |
| **Migrations** | Flyway 9 | Flyway 10 |

**Por que PostgreSQL?**
- Melhor suporte a indices parciais e expressoes (uteis para filtros de alimentos por faixa de calorias)
- `GENERATED ALWAYS AS` para colunas calculadas (ex: `calorias_restantes` no registro diario)
- Suporte nativo a JSONB para armazenar metadados flexiveis de alimentos
- Melhor performance em queries analiticas (dashboards semanais/mensais)

**Por que Redis?**
- Cache da tabela de alimentos e categorias (dados que raramente mudam)
- Cache de calculos diarios do usuario (evita recalcular toda vez que o dashboard e acessado)
- Rate limiting com `Bucket4j` + Redis para protecao contra abuso nos endpoints publicos
- Sessoes distribuidas se a API escalar horizontalmente

---

## 5. Observabilidade

O projeto atual nao possui nenhuma camada de observabilidade. Em 2026, as tres colunas seriam implementadas:

### 5.1. Logging (ELK / Loki)

```
Spring Boot → Logback JSON → Filebeat/Promtail → Elasticsearch + Kibana / Loki + Grafana
```

- Logs estruturados em JSON com `correlation_id` por requisicao
- Agregacao de logs entre multiplas instancias

### 5.2. Metricas (Micrometer + Prometheus + Grafana)

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Metricas essenciais:
- **Negocio**: refeicoes registradas/hora, usuarios ativos/dia, taxa de conclusao de metas
- **Tecnicas**: latencia p50/p95/p99 por endpoint, taxa de erro, uso de heap, conexoes ativas no pool
- **Health checks**: Actuator `/health` com probes de liveness e readiness para Kubernetes

### 5.3. Tracing Distribuido (OpenTelemetry)

Com `spring-boot-starter-actuator` + `micrometer-tracing-bridge-otel`, cada requisicao geraria um `traceId` propagado entre servicos, permitindo rastrear o fluxo completo de uma chamada no Jaeger ou Grafana Tempo.

---

## 6. Mensageria e Arquitetura Assincrona

Varias funcionalidades do api-nutricao se beneficiariam de processamento assincrono com **RabbitMQ** ou **Apache Kafka**:

| Funcionalidade | Gatilho | Evento | Consumidor |
|---|---|---|---|
| Notificacao de meta batida | `RegistroDiarioService` ao detectar 100% da meta | `MetaAlcancadaEvent` | Envio de email/notificacao push |
| Relatorio semanal | Cron semanal (domingo 23:59) | `GerarRelatorioSemanalEvent` | Geracao de PDF com resumo nutricional |
| Sugestao de alimentos | Usuario atinge 80% da meta de proteinas | `RefeicaoRegistradaEvent` | Motor de recomendacao (alimentos ricos em proteina) |
| Auditoria de alteracoes | Qualquer `PUT/PATCH/DELETE` | `EntidadeAlteradaEvent` | Log de auditoria em tabela separada |

**Padrao Transactional Outbox**: Garantir que o evento so seja publicado se a transacao no banco for confirmada, evitando inconsistencia entre banco e mensageria.

---

## 7. CI/CD com GitHub Actions

Substituir o deploy manual (`git pull → mvn package → systemctl restart`) por pipelines automatizadas:

```yaml
# .github/workflows/ci.yml (simplificado)
name: CI
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    services:
      postgres:
        image: postgres:16
        options: --health-cmd pg_isready
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with: { java-version: 21, distribution: temurin }
      - run: mvn verify jacoco:report
      - uses: sonarsource/sonarqube-scan-action@v3
```

```yaml
# .github/workflows/deploy.yml
name: Deploy
on:
  push:
    branches: [main]
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - run: mvn package -DskipTests
      - uses: docker/build-push-action@v5
        with: { push: true, tags: registry.exemplo.com/api-nutricao:latest }
      - run: kubectl rollout restart deployment/api-nutricao
```

---

## 8. Testes Modernos

### 8.1. Testcontainers

Substituir H2 em memoria por **Testcontainers** com PostgreSQL real nos testes de integracao. O H2 tem diferencas sutis de SQL que podem mascarar bugs (ex: funcoes de data, restricoes de FK, collation).

```java
@SpringBootTest
@Testcontainers
class UsuarioIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");
    
    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
```

### 8.2. Contract Testing (Pact)

Se a API tiver clientes mobile ou frontend, testes de contrato garantiriam que mudancas nos DTOs nao quebrassem consumidores.

### 8.3. Property-Based Testing (jqwik)

Para entidades como `MetaNutricional` e `RegistroDiario` com regras de negocio complexas (ex: calorias consumidas nunca podem ser negativas), testes baseados em propriedades gerariam milhares de cenarios aleatorios automaticamente.

---

## 9. Seguranca

| Aspecto | 2023 | 2026 |
|---|---|---|
| **Autenticacao** | JWT custom (auth0 java-jwt) | OAuth2/OpenID Connect (Spring Security 6) |
| **Autorizacao** | Filtro manual por token | `@PreAuthorize` + method security |
| **Secrets** | `application.properties` / systemd env | AWS Secrets Manager / Vault |
| **HTTPS** | Nginx + Certbot | Cert-Manager (Kubernetes) |
| **Rate Limiting** | Nenhum | Bucket4j + Redis |

**Spring Security 6 + OAuth2**: Usar **Keycloak** como Identity Provider (gratuito, open-source) em vez de implementar autenticacao customizada. Alem de login com email/senha, habilitaria login social (Google, Apple) e 2FA sem esforco adicional.

**API Gateway**: Se a aplicacao evoluir para microservicos, um **Spring Cloud Gateway** centralizaria autenticacao, rate limiting e roteamento.

---

## 10. API Design

### 10.1. GraphQL para Dashboards

Endpoints REST sao excelentes para CRUD, mas o dashboard nutricional precisa de dados agregados de multiplas entidades (refeicoes, metas, registro diario). **GraphQL** com `spring-boot-starter-graphql` permitiria ao frontend mobile solicitar exatamente os dados que precisa em uma unica chamada:

```graphql
query Dashboard($data: Date!) {
  dashboard(data: $data) {
    caloriasConsumidas
    caloriasMeta
    proteinasConsumidas
    proteinasMeta
    refeicoes { tipo alimentos { nome quantidade calorias } }
    progressoSemanal { data calorias }
  }
}
```

### 10.2. API Versioning

Adicionar versionamento via header (`Accept: application/vnd.nutricao.v2+json`) ou path (`/api/v2/...`) para evolucao sem quebra de contrato com clientes existentes.

---

## 11. Resumo Comparativo

| Dimensao | 2023 | 2026 |
|---|---|---|
| **Linguagem** | Java 11 | Java 21 LTS |
| **Framework** | Spring Boot 2.7.18 | Spring Boot 3.3.x |
| **Build** | Maven | Maven + GraalVM Native |
| **Banco** | MySQL 8 | PostgreSQL 16 + Redis 7 |
| **Container** | Docker manual | Docker + Kubernetes / K3s |
| **Observabilidade** | Nenhuma | Actuator + Prometheus + Grafana + OTEL |
| **Mensageria** | Nenhuma | RabbitMQ / Kafka + Outbox |
| **CI/CD** | Deploy manual (SSH) | GitHub Actions + SonarQube |
| **Testes** | H2 em memoria | Testcontainers + Pact + jqwik |
| **Seguranca** | JWT custom + BCrypt | Keycloak (OAuth2/OIDC) |
| **API** | REST | REST + GraphQL |
| **Infra** | EC2 + Nginx manual | EKS + Helm + Cert-Manager |

---

## 12. Roteiro de Adocao (Roadmap)

Se o projeto fosse modernizado incrementalmente, a ordem sugerida seria:

```
Fase 1 (fundacao)
├── Migrar para Java 21 + Spring Boot 3.3
├── Substituir records nos DTOs
├── Adicionar Spring Boot Actuator + health checks
└── Adicionar Testcontainers nos testes de integracao

Fase 2 (infra e qualidade)
├── Pipeline CI/CD com GitHub Actions
├── Redis para cache de alimentos e categorias
├── Prometheus + Grafana para metricas
└── Substituir H2 por PostgreSQL nos testes

Fase 3 (arquitetura)
├── Mensageria para eventos de dominio (RabbitMQ)
├── GraphQL para endpoints de dashboard
├── Kubernetes (K3s para dev, EKS para prod)
└── Keycloak para autenticacao (OAuth2/OIDC)

Fase 4 (avancado)
├── GraalVM Native Image para cold start < 100ms
├── OpenTelemetry para tracing distribuido
├── Contract testing com Pact
└── Multi-tenancy para suportar nutricionistas com multiplos pacientes
```

---

[← Voltar ao README principal](../README.md)
