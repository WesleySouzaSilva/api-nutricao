# Planejamento TDD

## Abordagem

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

## Ciclos de Implementação

Os ciclos foram estruturados para seguir TDD, começando pelas entidades e serviços (domain layer) antes dos controllers (API layer).

### Ciclo 1 — Projeto Base e Infraestrutura ✅
*Arquivos: 8 | Depende de: N/A*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 1.1 | Criar projeto Maven com Spring Boot 2.7.18 | ❌ | `pom.xml` ✅ |
| 1.2 | Configurar application.properties + profiles dev/prod/test | ❌ | `application.properties`, `application-dev.properties`, `application-prod.properties`, `application-test.properties` ✅ |
| 1.3 | Classe principal ApiNutricaoApplication | ❌ | `ApiNutricaoApplication.java` ✅ |
| 1.4 | Docker Compose (MySQL 8 + app) + Dockerfile | ❌ | `Dockerfile`, `docker-compose.yml` ✅ |
| 1.5 | .gitignore + .editorconfig | ❌ | `.gitignore`, `.editorconfig` ✅ |

### Ciclo 2 — TDD: Entidades e Migrações ✅
*Arquivos: 10 | Depende de: Ciclo 1*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 2.1 | **Teste** → Entidade Usuario | ✅ | `UsuarioTest.java`, `Usuario.java` |
| 2.2 | **Teste** → Entidade CategoriaAlimento | ✅ | `CategoriaAlimentoTest.java`, `CategoriaAlimento.java` |
| 2.3 | **Teste** → Entidade Alimento | ✅ | `AlimentoTest.java`, `Alimento.java` |
| 2.4 | **Teste** → Entidade Refeicao + AlimentoRefeicao | ✅ | `RefeicaoTest.java`, `Refeicao.java`, `AlimentoRefeicao.java` |
| 2.5 | **Teste** → Entidade MetaNutricional | ✅ | `MetaNutricionalTest.java`, `MetaNutricional.java` |
| 2.6 | **Teste** → Entidade Objetivo | ✅ | `ObjetivoTest.java`, `Objetivo.java` |
| 2.7 | **Teste** → Entidade RegistroDiario | ✅ | `RegistroDiarioTest.java`, `RegistroDiario.java` |
| 2.8 | **Teste** → Entidade AlimentoFavorito | ✅ | `AlimentoFavoritoTest.java`, `AlimentoFavorito.java` |
| 2.9 | Flyway V1: todas as tabelas | ❌ | `V1__create_tables.sql` ✅ |

### Ciclo 3 — TDD: Repositories
*Arquivos: 8 | Depende de: Ciclo 2*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 3.1 | **Teste** → UsuarioRepository (findByEmail) | ✅ | `UsuarioRepositoryTest.java`, `UsuarioRepository.java` |
| 3.2 | **Teste** → AlimentoRepository (filtros, search) | ✅ | `AlimentoRepositoryTest.java`, `AlimentoRepository.java` |
| 3.3 | **Teste** → RefeicaoRepository (queries por período) | ✅ | `RefeicaoRepositoryTest.java`, `RefeicaoRepository.java` |
| 3.4 | **Teste** → AlimentoSpecification | ✅ | `AlimentoSpecificationTest.java`, `AlimentoSpecification.java` |
| 3.5 | **Teste** → MetaNutricionalRepository | ✅ | `MetaNutricionalRepositoryTest.java`, `MetaNutricionalRepository.java` |
| 3.6 | **Teste** → ObjetivoRepository | ✅ | `ObjetivoRepositoryTest.java`, `ObjetivoRepository.java` |
| 3.7 | **Teste** → RegistroDiarioRepository | ✅ | `RegistroDiarioRepositoryTest.java`, `RegistroDiarioRepository.java` |

### Ciclo 4 — TDD: Services e Autenticação
*Arquivos: 16 | Depende de: Ciclo 3*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 4.1 | **Teste** → JwtUtil (gerar/validar token) | ✅ | `JwtUtilTest.java`, `JwtUtil.java` |
| 4.2 | **Teste** → UsuarioService (criar, buscar, email duplicado) | ✅ | `UsuarioServiceTest.java`, `UsuarioService.java` |
| 4.3 | **Teste** → AlimentoService (CRUD, filtros) | ✅ | `AlimentoServiceTest.java`, `AlimentoService.java` |
| 4.4 | **Teste** → RefeicaoService (calcular calorias, resumo) | ✅ | `RefeicaoServiceTest.java`, `RefeicaoService.java` |
| 4.5 | **Teste** → MetaNutricionalService | ✅ | `MetaNutricionalServiceTest.java`, `MetaNutricionalService.java` |
| 4.6 | **Teste** → ObjetivoService | ✅ | `ObjetivoServiceTest.java`, `ObjetivoService.java` |
| 4.7 | **Teste** → RegistroDiarioService | ✅ | `RegistroDiarioServiceTest.java`, `RegistroDiarioService.java` |
| 4.8 | SecurityConfig (SecurityFilterChain, CORS, BCrypt) | ❌ | `SecurityConfig.java` |
| 4.9 | JwtAuthenticationFilter (OncePerRequestFilter) | ❌ | `JwtAuthenticationFilter.java` |

### Ciclo 5 — TDD: Controllers (Auth + Usuários)
*Arquivos: 8 | Depende de: Ciclo 4*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 5.1 | **Teste** → AuthController (register + login) | ✅ | `AuthControllerTest.java`, `AuthController.java` |
| 5.2 | **Teste** → UsuarioController (me, update, delete) | ✅ | `UsuarioControllerTest.java`, `UsuarioController.java` |
| 5.3 | DTOs de request/response (Auth + Usuario) | ❌ | `LoginRequest.java`, `UsuarioCreateRequest.java`, `UsuarioUpdateRequest.java`, `TokenResponse.java`, `UsuarioResponse.java` |

### Ciclo 6 — TDD: Controllers (Alimentos + Categorias)
*Arquivos: 8 | Depende de: Ciclo 5*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 6.1 | **Teste** → CategoriaController | ✅ | `CategoriaControllerTest.java`, `CategoriaController.java` |
| 6.2 | **Teste** → AlimentoController (CRUD + favoritos) | ✅ | `AlimentoControllerTest.java`, `AlimentoController.java` |
| 6.3 | DTOs de Alimento + Categoria | ❌ | `AlimentoRequest.java`, `AlimentoResponse.java`, `CategoriaRequest.java`, `CategoriaResponse.java` |

### Ciclo 7 — TDD: Controllers (Refeições + Metas + Objetivos + Registro)
*Arquivos: 12 | Depende de: Ciclo 6*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 7.1 | **Teste** → RefeicaoController (CRUD + resumos) | ✅ | `RefeicaoControllerTest.java`, `RefeicaoController.java` |
| 7.2 | **Teste** → MetaNutricionalController | ✅ | `MetaNutricionalControllerTest.java`, `MetaNutricionalController.java` |
| 7.3 | **Teste** → ObjetivoController | ✅ | `ObjetivoControllerTest.java`, `ObjetivoController.java` |
| 7.4 | **Teste** → RegistroDiarioController | ✅ | `RegistroDiarioControllerTest.java`, `RegistroDiarioController.java` |
| 7.5 | DTOs de Refeição, Meta, Objetivo, Registro | ❌ | DTOs request/response |

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

[← Voltar ao README principal](../README.md)
