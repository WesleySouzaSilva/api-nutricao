# 🥗 api-nutricao

API REST para controle alimentar e nutricional — projeto portfólio demonstrando boas práticas com **Spring Boot**, **TDD**, **Clean Architecture** e **padrões profissionais de desenvolvimento**.

> **Status**: Em desenvolvimento — Planejamento e estruturação inicial (PR #1)
> **Stack**: Java 11 | Spring Boot 2.7.18 | MySQL 8 | Flyway | JWT | Docker

---

## 📋 Índice

- [Visão Geral](#-visão-geral)
- [Stack Tecnológica](#-stack-tecnológica)
- [Entidades do Domínio](#-entidades-do-domínio)
- [Endpoints da API](#-endpoints-da-api)
- [Abordagem TDD](#-abordagem-tdd)
- [Ciclos de Implementação](#-ciclos-de-implementação)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Como Executar](#-como-executar)
- [Padrões de Commit e Branch](#-padrões-de-commit-e-branch)
- [Próximos Passos](#-próximos-passos)

---

## 🎯 Visão Geral

O **api-nutricao** é uma API REST desenvolvida do zero para demonstrar competências em engenharia de software Java/Spring. O sistema permite:

- ✅ Cadastro e autenticação de usuários com JWT
- ✅ Tabela nutricional completa com alimentos e categorias
- ✅ Registro de refeições diárias com cálculo automático de calorias e macronutrientes
- ✅ Metas nutricionais personalizadas (calorias, proteínas, carboidratos, gorduras)
- ✅ Acompanhamento diário de peso, ingestão de água e passos
- ✅ Dashboard com progresso diário e semanal
- ✅ Documentação OpenAPI (Swagger)
- ✅ Tratamento de erros padronizado (RFC 7807)
- ✅ Docker Compose para ambiente completo

### Diferenciais

| Característica | api-nutricao | api-mbs (referência) |
|---|---|---|
| **Abordagem** | TDD (test-first) | Sem testes |
| **Migrações** | Flyway versionado | Manual |
| **Entidades** | 8, enxutas e focadas | 14+, com acoplamento excessivo |
| **Exception Handling** | RFC 7807 com handler global | RFC 7807 |
| **Documentação** | SpringDoc OpenAPI | SpringFox (deprecated) |
| **Container** | Docker + Compose ativo | Docker comentado no POM |
| **Segurança** | JWT 24h + BCrypt + env vars | JWT 30 dias + secrets hardcoded |

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
| **Documentação** | SpringDoc OpenAPI | 1.8.0 | Sucessor do SpringFox, compatível com SB 2.x |
| **Mapping** | ModelMapper | 3.2.1 | Conversão entidade ↔ DTO |
| **Validação** | Hibernate Validator | (via starter) | Bean Validation |
| **Testes** | JUnit 5 + Mockito | Última | TDD desde o início |
| **Container** | Docker + Compose | Última | Ambiente reproduzível |
| **CI/CD** | GitHub Actions | — | Build e testes automatizados |

---

## 📦 Entidades do Domínio

```
┌──────────────┐     ┌──────────────────┐     ┌───────────────────┐     ┌──────────────────┐
│     User     │1──N│    Refeicao      │1──N│ AlimentoRefeicao  │N──1│    Alimento      │
├──────────────┤     ├──────────────────┤     ├───────────────────┤     ├──────────────────┤
│ id (PK)      │     │ id (PK)          │     │ id (PK)           │     │ id (PK)          │
│ nome         │     │ usuario_id (FK)  │     │ refeicao_id (FK)  │     │ nome             │
│ email        │     │ tipo             │     │ alimento_id (FK)  │     │ categoria_id (FK)│──┐
│ senha (hash) │     │ data_refeicao    │     │ quantidade        │     │ calorias         │  │
│ data_nasc    │     │ observacao       │     │ calorias (calc)   │     │ proteina         │  │
│ sexo         │     │ created_at       │     │ created_at        │     │ carboidrato      │  │
│ altura       │     └──────────────────┘     └───────────────────┘     │ gordura          │  │
│ created_at   │                                                         │ fibra            │  │
│ updated_at   │                                                         └──────────────────┘  │
└──────┬───────┘                                                                              │
       │1                                                                                      │
       │                                                                                       │
       │1              ┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐   │
       ├──────────────N│  MetaNutricional │     │ RegistroDiario   │     │  CategoriaAlim   │───┘
       │               ├──────────────────┤     ├──────────────────┤     ├──────────────────┤
       │               │ id (PK)          │     │ id (PK)          │     │ id (PK)          │
       │               │ usuario_id (FK)  │     │ usuario_id (FK)  │     │ nome             │
       │               │ tipo (CALORIA/   │     │ data             │     │ created_at       │
       │               │   PROTEINA/      │     │ peso_jejum       │     └──────────────────┘
       │               │   CARBOIDRATO/   │     │ agua_ml          │
       │               │   GORDURA)       │     │ passos           │
       │               │ valor_meta       │     │ created_at       │
       │               │ data_inicio      │     └──────────────────┘
       │               │ created_at       │
       │               └──────────────────┘
       │
       │1              ┌──────────────────────┐
       └──────────────N│ AlimentoFavorito      │
                       ├──────────────────────┤
                       │ id (PK)              │
                       │ usuario_id (FK)      │
                       │ alimento_id (FK)     │
                       │ created_at           │
                       └──────────────────────┘
```

### Descrição das Entidades

| Entidade | Tabela | Finalidade | Cache/Qtde |
|---|---|---|---|
| **User** | `users` | Cadastro e autenticação de usuários | 1 por usuário |
| **CategoriaAlimento** | `categorias_alimento` | Classificação de alimentos (Laticínios, Carnes, Frutas, etc.) | ~15 registros |
| **Alimento** | `alimentos` | Tabela nutricional com calorias, proteínas, carboidratos, gorduras, fibras, sódio | ~50-100 registros |
| **Refeicao** | `refeicoes` | Registro de refeição com tipo (CAFE_DA_MANHA, ALMOCO, JANTAR, LANCHE) | ~300/dia por usuário |
| **AlimentoRefeicao** | `refeicoes_alimentos` | Itens consumidos em cada refeição com quantidade e calorias calculadas | ~5-10 por refeição |
| **MetaNutricional** | `metas_nutricionais` | Metas diárias de calorias, proteínas, carboidratos e gorduras | ~4 por usuário |
| **RegistroDiario** | `registros_diarios` | Acompanhamento diário: peso jejum, água ingerida (ml), passos | 1 por dia por usuário |
| **AlimentoFavorito** | `alimentos_favoritos` | Alimentos marcados como favoritos para acesso rápido | ~10-20 por usuário |

---

## 🌐 Endpoints da API

### Autenticação (`/v1/auth`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `POST` | `/v1/auth/register` | Cadastrar novo usuário | Aberto |
| `POST` | `/v1/auth/login` | Login (email + senha) → JWT | Aberto |

### Usuários (`/v1/usuarios`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/usuarios/me` | Perfil do usuário logado | JWT |
| `PUT` | `/v1/usuarios/me` | Atualizar perfil | JWT |
| `DELETE` | `/v1/usuarios/me` | Excluir conta | JWT |

### Categorias (`/v1/categorias`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/categorias` | Listar todas as categorias | JWT |
| `POST` | `/v1/categorias` | Criar nova categoria | JWT |
| `DELETE` | `/v1/categorias/{id}` | Remover categoria | JWT |

### Alimentos (`/v1/alimentos`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/alimentos` | Listar (paginado, filtro por nome/categoria) | JWT |
| `GET` | `/v1/alimentos/{id}` | Detalhe completo com informação nutricional | JWT |
| `POST` | `/v1/alimentos` | Cadastrar novo alimento | JWT |
| `PUT` | `/v1/alimentos/{id}` | Atualizar alimento | JWT |
| `DELETE` | `/v1/alimentos/{id}` | Remover alimento | JWT |
| `GET` | `/v1/alimentos/favoritos` | Listar alimentos favoritos do usuário | JWT |
| `POST` | `/v1/alimentos/{id}/favoritar` | Favoritar/desfavoritar alimento | JWT |

### Refeições (`/v1/refeicoes`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/refeicoes` | Listar refeições do usuário (filtro por data) | JWT |
| `GET` | `/v1/refeicoes/{id}` | Detalhe da refeição com alimentos consumidos | JWT |
| `POST` | `/v1/refeicoes` | Registrar refeição com lista de alimentos + quantidades | JWT |
| `PUT` | `/v1/refeicoes/{id}` | Atualizar refeição | JWT |
| `DELETE` | `/v1/refeicoes/{id}` | Remover refeição | JWT |
| `GET` | `/v1/refeicoes/resumo/diario` | Resumo nutricional do dia (totais + por refeição) | JWT |
| `GET` | `/v1/refeicoes/resumo/semanal` | Resumo nutricional dos últimos 7 dias | JWT |

### Metas Nutricionais (`/v1/metas`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/metas` | Listar metas do usuário | JWT |
| `POST` | `/v1/metas` | Criar meta (calorias, proteína, carboidrato, gordura) | JWT |
| `PUT` | `/v1/metas/{id}` | Atualizar meta | JWT |
| `DELETE` | `/v1/metas/{id}` | Remover meta | JWT |

### Registro Diário (`/v1/registros`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `POST` | `/v1/registros/diario` | Registrar peso/água/passos do dia | JWT |
| `GET` | `/v1/registros/diario/hoje` | Último registro do dia | JWT |
| `GET` | `/v1/registros/diario/historico` | Histórico por período | JWT |

### Dashboard (`/v1/dashboard`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/dashboard/hoje` | Progresso de hoje vs metas (calorias consumidas, água, passos) | JWT |
| `GET` | `/v1/dashboard/semanal` | Evolução dos últimos 7 dias (gráfico de consumo vs meta) | JWT |

### Health Check

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/health` | Health check da aplicação | Aberto |

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

### Matriz de Testes

| Camada | Tipo | Framework | O que testar |
|--------|------|-----------|-------------|
| Entity | Unitário | JUnit 5 | Validações, cálculos, timestamps |
| Repository | Integração | @DataJpaTest | Queries customizadas, derived queries |
| Service | Unitário | JUnit 5 + Mockito | Regras de negócio, validações, exceções |
| Controller | Integração | @WebMvcTest | Status codes, body, headers, validação de entrada |
| Fluxo completo | Integração | @SpringBootTest | Register → Login → CRUD → Resumo |

---

## 📐 Ciclos de Implementação

Os ciclos foram reestruturados para seguir TDD, começando pelas entidades e serviços (domain layer) antes dos controllers (API layer).

### Ciclo 1 — Projeto Base e Infraestrutura
*Arquivos: 8 | Depende de: N/A*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 1.1 | Criar projeto Maven com Spring Boot 2.7.18 | ❌ | `pom.xml` |
| 1.2 | Configurar application.yml + profiles dev/prod | ❌ | `application.yml`, `application-dev.yml`, `application-prod.yml` |
| 1.3 | Classe principal ApiNutricaoApplication | ❌ | `ApiNutricaoApplication.java` |
| 1.4 | Docker Compose (MySQL 8 + app) + Dockerfile | ❌ | `Dockerfile`, `docker-compose.yml` |
| 1.5 | .gitignore + .editorconfig | ❌ | `.gitignore`, `.editorconfig` |

### Ciclo 2 — TDD: Entidades e Migrações
*Arquivos: 10 | Depende de: Ciclo 1*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 2.1 | **Teste** → Entidade User | ✅ | `UserTest.java`, `User.java` |
| 2.2 | **Teste** → Entidade CategoriaAlimento | ✅ | `CategoriaAlimentoTest.java`, `CategoriaAlimento.java` |
| 2.3 | **Teste** → Entidade Alimento | ✅ | `AlimentoTest.java`, `Alimento.java` |
| 2.4 | **Teste** → Entidade Refeicao + AlimentoRefeicao | ✅ | `RefeicaoTest.java`, `Refeicao.java`, `AlimentoRefeicao.java` |
| 2.5 | **Teste** → Entidade MetaNutricional | ✅ | `MetaNutricionalTest.java`, `MetaNutricional.java` |
| 2.6 | **Teste** → Entidade RegistroDiario | ✅ | `RegistroDiarioTest.java`, `RegistroDiario.java` |
| 2.7 | **Teste** → Entidade AlimentoFavorito | ✅ | `AlimentoFavoritoTest.java`, `AlimentoFavorito.java` |
| 2.8 | Flyway V1: todas as tabelas | ❌ | `V1__create_tables.sql` |

### Ciclo 3 — TDD: Repositories
*Arquivos: 8 | Depende de: Ciclo 2*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 3.1 | **Teste** → UserRepository (findByEmail) | ✅ | `UserRepositoryTest.java`, `UserRepository.java` |
| 3.2 | **Teste** → AlimentoRepository (filtros, search) | ✅ | `AlimentoRepositoryTest.java`, `AlimentoRepository.java` |
| 3.3 | **Teste** → RefeicaoRepository (queries por período) | ✅ | `RefeicaoRepositoryTest.java`, `RefeicaoRepository.java` |
| 3.4 | **Teste** → AlimentoSpecification | ✅ | `AlimentoSpecificationTest.java`, `AlimentoSpecification.java` |
| 3.5 | **Teste** → MetaNutricionalRepository | ✅ | `MetaNutricionalRepositoryTest.java`, `MetaNutricionalRepository.java` |
| 3.6 | **Teste** → RegistroDiarioRepository | ✅ | `RegistroDiarioRepositoryTest.java`, `RegistroDiarioRepository.java` |

### Ciclo 4 — TDD: Services e Autenticação
*Arquivos: 12 | Depende de: Ciclo 3*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 4.1 | **Teste** → JwtUtil (gerar/validar token) | ✅ | `JwtUtilTest.java`, `JwtUtil.java` |
| 4.2 | **Teste** → UserService (criar, buscar, email duplicado) | ✅ | `UserServiceTest.java`, `UserService.java` |
| 4.3 | **Teste** → AlimentoService (CRUD, filtros) | ✅ | `AlimentoServiceTest.java`, `AlimentoService.java` |
| 4.4 | **Teste** → RefeicaoService (calcular calorias, resumo) | ✅ | `RefeicaoServiceTest.java`, `RefeicaoService.java` |
| 4.5 | **Teste** → MetaNutricionalService | ✅ | `MetaNutricionalServiceTest.java`, `MetaNutricionalService.java` |
| 4.6 | **Teste** → RegistroDiarioService | ✅ | `RegistroDiarioServiceTest.java`, `RegistroDiarioService.java` |
| 4.7 | SecurityConfig (SecurityFilterChain, CORS, BCrypt) | ❌ | `SecurityConfig.java` |
| 4.8 | JwtAuthenticationFilter (OncePerRequestFilter) | ❌ | `JwtAuthenticationFilter.java` |

### Ciclo 5 — TDD: Controllers (Auth + Usuários)
*Arquivos: 8 | Depende de: Ciclo 4*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 5.1 | **Teste** → AuthController (register + login) | ✅ | `AuthControllerTest.java`, `AuthController.java` |
| 5.2 | **Teste** → UserController (me, update, delete) | ✅ | `UserControllerTest.java`, `UserController.java` |
| 5.3 | DTOs de request/response (Auth + User) | ❌ | `LoginRequest.java`, `UserCreateRequest.java`, `UserUpdateRequest.java`, `TokenResponse.java`, `UserResponse.java` |

### Ciclo 6 — TDD: Controllers (Alimentos + Categorias)
*Arquivos: 8 | Depende de: Ciclo 5*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 6.1 | **Teste** → CategoriaController | ✅ | `CategoriaControllerTest.java`, `CategoriaController.java` |
| 6.2 | **Teste** → AlimentoController (CRUD + favoritos) | ✅ | `AlimentoControllerTest.java`, `AlimentoController.java` |
| 6.3 | DTOs de Alimento + Categoria | ❌ | `AlimentoRequest.java`, `AlimentoResponse.java`, `CategoriaRequest.java`, `CategoriaResponse.java` |

### Ciclo 7 — TDD: Controllers (Refeições + Metas + Registro)
*Arquivos: 12 | Depende de: Ciclo 6*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 7.1 | **Teste** → RefeicaoController (CRUD + resumos) | ✅ | `RefeicaoControllerTest.java`, `RefeicaoController.java` |
| 7.2 | **Teste** → MetaNutricionalController | ✅ | `MetaNutricionalControllerTest.java`, `MetaNutricionalController.java` |
| 7.3 | **Teste** → RegistroDiarioController | ✅ | `RegistroDiarioControllerTest.java`, `RegistroDiarioController.java` |
| 7.4 | **Teste** → DashboardController | ✅ | `DashboardControllerTest.java`, `DashboardController.java` |
| 7.5 | DTOs de Refeição, Meta, Registro, Dashboard | ❌ | DTOs request/response |

### Ciclo 8 — Exception Handling, Documentação e Finalização
*Arquivos: 8 | Depende de: Ciclo 7*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 8.1 | **Teste** → ApiExceptionHandler | ✅ | `ApiExceptionHandlerTest.java`, `ApiExceptionHandler.java` |
| 8.2 | Modelo RFC 7807 (Problema + TipoProblema) | ❌ | `Problema.java`, `TipoProblema.java` |
| 8.3 | Exceções customizadas | ❌ | `EntidadeNaoEncontradaException.java`, `EntidadeEmUsoException.java`, `NegocioException.java` |
| 8.4 | OpenApiConfig (SpringDoc) | ❌ | `OpenApiConfig.java` |
| 8.5 | Seed data (alimentos comuns) | ❌ | `V2__seed_alimentos.sql` |
| 8.6 | Build final: `mvn clean package` | ❌ | — |
| 8.7 | Testes completos: `mvn clean test` | ❌ | — |

---

## 📁 Estrutura do Projeto

```
api-nutricao/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── .gitignore
├── .editorconfig
├── padrao estrutura github.txt
├── README.md
├── src/
│   ├── main/
│   │   ├── java/br/com/nutricao/
│   │   │   ├── ApiNutricaoApplication.java
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── OpenApiConfig.java
│   │   │   ├── domain/
│   │   │   │   ├── model/
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── CategoriaAlimento.java
│   │   │   │   │   ├── Alimento.java
│   │   │   │   │   ├── Refeicao.java
│   │   │   │   │   ├── AlimentoRefeicao.java
│   │   │   │   │   ├── MetaNutricional.java
│   │   │   │   │   ├── RegistroDiario.java
│   │   │   │   │   └── AlimentoFavorito.java
│   │   │   │   ├── repository/
│   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   ├── AlimentoRepository.java
│   │   │   │   │   ├── RefeicaoRepository.java
│   │   │   │   │   ├── MetaNutricionalRepository.java
│   │   │   │   │   └── RegistroDiarioRepository.java
│   │   │   │   └── service/
│   │   │   │       ├── UserService.java
│   │   │   │       ├── AlimentoService.java
│   │   │   │       ├── RefeicaoService.java
│   │   │   │       ├── MetaNutricionalService.java
│   │   │   │       └── RegistroDiarioService.java
│   │   │   ├── api/
│   │   │   │   ├── controller/
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── UserController.java
│   │   │   │   │   ├── CategoriaController.java
│   │   │   │   │   ├── AlimentoController.java
│   │   │   │   │   ├── RefeicaoController.java
│   │   │   │   │   ├── MetaNutricionalController.java
│   │   │   │   │   ├── RegistroDiarioController.java
│   │   │   │   │   ├── DashboardController.java
│   │   │   │   │   └── HealthController.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── request/
│   │   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   │   ├── UserCreateRequest.java
│   │   │   │   │   │   ├── UserUpdateRequest.java
│   │   │   │   │   │   ├── CategoriaRequest.java
│   │   │   │   │   │   ├── AlimentoRequest.java
│   │   │   │   │   │   ├── RefeicaoRequest.java
│   │   │   │   │   │   └── ... (demais requests)
│   │   │   │   │   └── response/
│   │   │   │   │       ├── TokenResponse.java
│   │   │   │   │       ├── UserResponse.java
│   │   │   │   │       ├── AlimentoResponse.java
│   │   │   │   │       ├── RefeicaoResponse.java
│   │   │   │   │       ├── ResumoDiarioResponse.java
│   │   │   │   │       └── ... (demais responses)
│   │   │   │   └── exception/
│   │   │   │       ├── ApiExceptionHandler.java
│   │   │   │       ├── Problema.java
│   │   │   │       ├── TipoProblema.java
│   │   │   │       ├── EntidadeNaoEncontradaException.java
│   │   │   │       ├── EntidadeEmUsoException.java
│   │   │   │       └── NegocioException.java
│   │   │   ├── security/
│   │   │   │   ├── JwtUtil.java
│   │   │   │   └── JwtAuthenticationFilter.java
│   │   │   └── infrastructure/
│   │   │       └── specification/
│   │   │           └── AlimentoSpecification.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       └── db/migration/
│   │           ├── V1__create_tables.sql
│   │           └── V2__seed_alimentos.sql
│   └── test/java/br/com/nutricao/
│       ├── domain/model/
│       │   ├── UserTest.java
│       │   ├── AlimentoTest.java
│       │   ├── RefeicaoTest.java
│       │   └── ... (demais testes de entidade)
│       ├── domain/repository/
│       │   ├── UserRepositoryTest.java
│       │   ├── AlimentoRepositoryTest.java
│       │   └── ... (demais testes de repository)
│       ├── domain/service/
│       │   ├── UserServiceTest.java
│       │   ├── AlimentoServiceTest.java
│       │   ├── RefeicaoServiceTest.java
│       │   └── ... (demais testes de service)
│       ├── api/controller/
│       │   ├── AuthControllerTest.java
│       │   ├── AlimentoControllerTest.java
│       │   └── ... (demais testes de controller)
│       └── security/
│           └── JwtUtilTest.java
```

**Total estimado**: ~75 arquivos (vs 44 do plano anterior)

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

A API estará disponível em `http://localhost:8080` e o Swagger em `http://localhost:8080/swagger-ui.html`.

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

## 📌 Próximos Passos

1. ✅ **PR #1** — Documentação e planejamento inicial (você está aqui)
2. ⏳ **PR #2** — Ciclo 1: Projeto base + infraestrutura (pom.xml, Docker, configs)
3. ⏳ **PR #3** — Ciclo 2: Entidades + migrações (TDD)
4. ⏳ **PR #4** — Ciclo 3: Repositories (TDD)
5. ⏳ **PR #5-6** — Ciclo 4-5: Services + Autenticação (TDD)
6. ⏳ **PR #7-9** — Ciclo 6-8: Controllers + Exception Handling + Final

---

<div align="center">
  <p>Desenvolvido como projeto portfólio — API REST para controle nutricional</p>
  <p>
    <a href="https://github.com/WesleySouzaSilva/api-nutricao">GitHub</a>
  </p>
</div>
