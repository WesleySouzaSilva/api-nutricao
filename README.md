# 🥗 api-nutricao

API REST para controle alimentar e nutricional — projeto portfólio demonstrando boas práticas com **Spring Boot**, **TDD**, **Clean Architecture** e **padrões profissionais de desenvolvimento**.

> **Status**: Concluido — 12 ciclos de implementacao (8 PRs)
> **Stack**: Java 11 | Spring Boot 2.7.18 | MySQL 8 | Flyway | JWT | Docker | AWS EC2 | Nginx | Certbot

---

## 📖 Histórico do Projeto

Este projeto é fruto de uma jornada de aprendizado contínua desde **2023**, quando adquiri a **Formação Especialista Spring REST** da [AlgaWorks](https://www.algaworks.com/), curso que me proporcionou uma base sólida em construção de APIs REST profissionais com Spring Boot. Junto a essa formação, cursei também a **Formação Especialista JPA**, aprofundando meus conhecimentos em mapeamento objeto-relacional, otimização de consultas e gerenciamento de entidades.

Com os conceitos aprendidos — especialmente do Especialista Spring REST — desenvolvi uma **API profissional para um cliente real** (projeto de referência **api-mbs**), aplicando na prática:

- Padrão Controller → Service → Repository
- Tratamento de erros no padrão RFC 7807
- Autenticação stateless com JWT
- Separação clara entre camadas com DTOs
- Especificações JPA para filtros dinâmicos

Em **2024**, dei continuidade aos estudos com a **Formação Especialista Microsserviços** (também da AlgaWorks), atualmente em andamento devido à extensão do conteúdo, que abrange tópicos como comunicação assíncrona, service discovery, configuração centralizada e observabilidade.

O **api-nutricao** nasce como um projeto portfólio que consolida todo esse aprendizado em uma aplicação enxuta, moderna e bem estruturada — agora evoluída com **TDD**, **Flyway**, **SpringDoc OpenAPI** e as boas práticas que adquiri ao longo dessa trajetória.

---

## 📋 Índice

- [Visão Geral](#-visão-geral)
- [Stack Tecnológica](#-stack-tecnológica)
- [Entidades do Domínio](docs/ENTIDADES.md)
- [Endpoints da API](docs/ENDPOINTS.md)
- [Abordagem TDD](#-abordagem-tdd)
- [Ciclos de Implementação](docs/PLANEJAMENTO.md#ciclos-de-implementação)
- [Estrutura do Projeto](docs/ESTRUTURA.md)
- [Deploy em Producao](docs/DEPLOY.md)
- [Como Executar](#-como-executar)
- [Padrões de Commit e Branch](#-padrões-de-commit-e-branch)
- [Pull Requests](#-pull-requests)

---

## 🎯 Visão Geral

O **api-nutricao** é uma API REST desenvolvida do zero para demonstrar competências em engenharia de software Java/Spring. O sistema permite:

- ✅ Cadastro e autenticação de usuários com JWT
- ✅ Tabela nutricional completa com alimentos e categorias
- ✅ Registro de refeições diárias com cálculo automático de calorias e macronutrientes
- ✅ Definição de objetivos (ganhar massa, reduzir gordura, manter peso)
- ✅ Metas nutricionais de macronutrientes (calorias, proteinas, carboidratos, gorduras)
- ✅ Acompanhamento diario de macronutrientes consumidos
- ✅ Dashboard com progresso diário e semanal
- ✅ Documentação OpenAPI (Swagger)
- ✅ Tratamento de erros padronizado (RFC 7807)
- ✅ Docker Compose para ambiente completo

### Diferenciais

| Característica | api-nutricao | api-mbs (referência) |
|---|---|---|
| **Abordagem** | TDD (test-first) | TDD (test-first) |
| **Migrações** | Flyway versionado | Manual |
| **Entidades** | 9, enxutas e focadas | 14+, com acoplamento excessivo |
| **Exception Handling** | RFC 7807 com handler global | RFC 7807 |
| **Documentação** | SpringDoc OpenAPI | SpringFox (deprecated) |
| **Container** | Docker + Compose ativo | Docker comentado no POM |
| **Segurança** | JWT 24h + BCrypt + env vars | Secrets hardcoded |

---

## 🛠 Stack Tecnológica

| Categoria | Tecnologia | Versão | Motivo |
|---|---|---|---|
| **Linguagem** | Java | 11 (LTS) | Consistente com projetos existentes |
| **Framework** | Spring Boot | 2.7.18 | Última release da linha 2.x com suporte estendido |
| **Build** | Maven | 3.9.9 | Já disponível no ambiente |
| **Banco** | MySQL | 8.0+ | Consistente com ecossistema |
| **Migration** | Flyway | 9.x | Controle de versão do schema |
| **Autenticação** | auth0 java-jwt | 4.4.0 | HMAC256, stateless |
| **Documentação** | SpringDoc OpenAPI | 1.7.0 | Sucessor do SpringFox, compatível com SB 2.x |
| **Mapping** | ModelMapper | 3.2.1 | Conversão entidade ↔ DTO |
| **Testes** | JUnit 5 + Mockito | Última | TDD desde o início |
| **Container** | Docker + Compose | Última | Ambiente reproduzível |

---

## 📦 Entidades do Domínio

Consulte o documento [`docs/ENTIDADES.md`](docs/ENTIDADES.md) para diagrama ER completo e descrição detalhada das 9 entidades.

---

## 🌐 Endpoints da API

Consulte o documento [`docs/ENDPOINTS.md`](docs/ENDPOINTS.md) para a lista completa de endpoints (10 controllers).

---

## 🧪 Abordagem TDD

O projeto segue **TDD (Test-Driven Development)** como metodologia principal de desenvolvimento. O ciclo é:

```
🔴 ESCREVER TESTE (falha)
   → Define o comportamento esperado antes da implementação
   → Teste compila mas falha (RED)

🟢 IMPLEMENTAR (passa)
   → Código mínimo para fazer o teste passar (GREEN)
   → Sem preocupação com elegância ainda

🟡 REFATORAR (melhora)
   → Melhora a qualidade do código
   → Testes continuam passando (REFACTOR)
```

### Ordem de Implementação (por camada)

Seguindo os princípios de **Clean Architecture** e **outside-in TDD**:

```
1. MODEL (entidades) ──────── Testes de entidade → Criação da entidade
       │
2. REPOSITORY ─────────────── Testes de repository → Interface + migration
       │
3. SERVICE ────────────────── Testes de service → Implementação da lógica
       │
4. CONTROLLER ─────────────── Testes de controller → Endpoints REST
       │
5. INTEGRAÇÃO ─────────────── Testes de fluxo completo
```

Matriz de testes e detalhes → [`docs/PLANEJAMENTO.md`](docs/PLANEJAMENTO.md)

---

## 📐 Ciclos de Implementação

Consulte o documento [`docs/PLANEJAMENTO.md`](docs/PLANEJAMENTO.md) para os 12 ciclos de implementacao com tarefas, dependencias e arquivos envolvidos.

---

## 📁 Estrutura do Projeto

Consulte o documento [`docs/ESTRUTURA.md`](docs/ESTRUTURA.md) para a arvore completa de arquivos (~145 no total) e detalhes de organizacao.

---

## 🚀 Como Executar

### Pré-requisitos

```bash
java -version          # Java 11+
mvn -version           # Maven 3.9+
docker --version       # Docker (opcional, para ambiente completo)
```

### Desenvolvimento (profile dev)

```bash
# Compilar
mvn clean compile

# Executar testes
mvn clean test

# Subir aplicação (requer MySQL local ou Docker)
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Produção (Docker Compose)

```bash
docker compose up --build
```

A API estará disponível em `http://localhost:8080/api/v1/...` e o Swagger em `http://localhost:8080/swagger-ui/index.html`.

---

## 🔖 Padrões de Commit e Branch

Seguimos o padrão descrito em [`padrao estrutura github.txt`](./padrao%20estrutura%20github.txt):

### Branches

```
AN-{numero}/descricao-resumida

Exemplos:
AN-01/criar-projeto
AN-02/implementar-entidades
AN-03/criar-servicos
```

### Commits

```
tipo(escopo): descrição

Tipos: feat, fix, docs, style, refactor, perf, test, chore
Exemplo: feat(alimento): criar CRUD de alimentos com filtros
```

### Pull Requests

```
[AN-{id}] tipo(escopo): descrição

Exemplo:
[AN-01] docs(projeto): criar documentacao e planejamento inicial
```

---

## 📌 Pull Requests

1. ✅ **PR #1** — [AN-01] docs(projeto): criar documentacao e planejamento inicial do projeto api-nutricao
2. ✅ **PR #2** — [AN-02] docs(planejamento): ajuste descricao do projeto
3. ✅ **PR #3** — [AN-03] feat(domain): implementar entidades do dominio com TDD e migracao Flyway V1
4. ✅ **PR #4** — [AN-04] feat(auth): implementar autenticacao com email/senha e token
5. ✅ **PR #5** — [AN-05] refactor(estrutura): reestruturar projeto conforme padrao api-mbs e implementar tratamento de erros
6. ✅ **PR #6** — [AN-06] feat(paginacao): implementar paginacao e filtros dinamicos com Specification
7. ✅ **PR #7** — [AN-07] docs(openapi): criar documentacao SpringDoc com interfaces, seed TACO e atualizar docs
8. 🔄 **PR #8** — [AN-08] docs(deploy): documentar deploy AWS EC2, ajustes finais e DEPLOY.md (atual)

---

<div align="center">
  <p>Desenvolvido como projeto portfólio — API REST para controle nutricional</p>
  <p>
    <a href="https://github.com/WesleySouzaSilva/api-nutricao">GitHub</a>
  </p>
</div>
