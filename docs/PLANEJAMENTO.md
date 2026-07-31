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

### Ciclo 8 — Exception Handling, Documentação e Finalização 🔧
*Arquivos: 8 | Depende de: Ciclo 7*
*Status: Parcialmente concluído — ApiExceptionHandler básico implementado; os itens restantes foram redistribuídos nos Ciclos 9, 10 e 11.*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 8.1 | **Teste** → ApiExceptionHandler | ✅ | `ApiExceptionHandlerTest.java`, `ApiExceptionHandler.java` ✅ *(versão básica: IllegalArgumentException + Exception genérica)* |
| 8.2 | ~~Modelo RFC 7807 (Problema + TipoProblema)~~ → movido para Ciclo 9 | ❌ | — |
| 8.3 | ~~Exceções customizadas~~ → movido para Ciclo 9 | ❌ | — |
| 8.4 | ~~OpenApiConfig (SpringDoc)~~ → movido para Ciclo 11 | ❌ | — |
| 8.5 | ~~Seed data (alimentos comuns)~~ → movido para Ciclo 11 | ❌ | — |
| 8.6 | ~~Build final: `mvn clean package`~~ → movido para Ciclo 11 | ❌ | — |
| 8.7 | Testes completos: `mvn clean test` | ❌ | ✅ 309 testes passando, JaCoCo 100% |

---

### Ciclo 9 — PR 1: Validações e Exceções (RFC 7807)
*Branch: `AN-05/ciclo-5-validacoes-excecoes` | Depende de: Ciclo 8*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 9.1 | Exceções customizadas | ✅ | `EntidadeNaoEncontradaException.java`, `EntidadeEmUsoException.java`, `NegocioException.java` |
| 9.2 | Modelo RFC 7807 (Problem Detail) | ❌ | `Problema.java`, `TipoProblema.java` |
| 9.3 | Expandir ApiExceptionHandler | ✅ | Handlers para: `EntidadeNaoEncontradaException` (404), `EntidadeEmUsoException` (409), `NegocioException` (422), `MethodArgumentNotValidException` (400), `DataIntegrityViolationException` (409) |
| 9.4 | Atualizar services para lançar exceções customizadas | ❌ | Substituir `throw new IllegalArgumentException(...)` por exceções específicas |
| 9.5 | Atualizar testes dos controllers e services | ✅ | Ajustar asserts para Problem Detail + status codes corretos |
| 9.6 | Verificação: `mvn test` (todos passando) | ❌ | — |

### Ciclo 10 — PR 2: Paginação e Filtros com Specification
*Branch: `AN-06/ciclo-6-paginacao-filtros` | Depende de: Ciclo 9*

**Padrão de referência (api-mbs):** Controller recebe `CamposFiltro` + `Pageable` → Service chama `repository.findAll(Specification, pageable)` → retorna `Page<DTO>`.

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 10.1 | Repository: adicionar `JpaSpecificationExecutor<T>` | ❌ | Todos os 9 repositories |
| 10.2 | Criar `CamposFiltro` (DTO de binding automático) | ✅ | `AlimentoCamposFiltro`, `RefeicaoCamposFiltro`, `RegistroDiarioCamposFiltro`, `MetaNutricionalCamposFiltro`, `ObjetivoCamposFiltro` |
| 10.3 | Criar `Specification` (Criteria API dinâmica) | ✅ | `AlimentoFiltro`, `RefeicaoFiltro`, `RegistroDiarioFiltro`, `MetaNutricionalFiltro`, `ObjetivoFiltro` |
| 10.4 | Refatorar Services: método único `listarTodosFiltro(filtro, pageable)` | ✅ | `AlimentoService`, `RefeicaoService`, `RegistroDiarioService`, `MetaNutricionalService`, `ObjetivoService` |
| 10.5 | Refatorar Controllers: GET listar → `Page<XxxResponse>` | ✅ | `AlimentoController`, `RefeicaoController`, `RegistroDiarioController`, `MetaNutricionalController`, `ObjetivoController` |
| 10.6 | Paginação simples no `CategoriaAlimentoController` | ❌ | Apenas `Pageable`, sem filtro dinâmico |
| 10.7 | Atualizar testes (controllers + services + repositories) | ✅ | Ajustar mocks para `Page<>`, testar filtros combinados |
| 10.8 | Verificação: `mvn test` (todos passando) | ❌ | — |

### Ciclo 11 — PR 3: Documentação e Seed de Dados
*Branch: `AN-07/ciclo-7-documentacao-seed` | Depende de: Ciclo 10*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 11.1 | SpringDoc OpenAPI | ❌ | `OpenApiConfig.java` — título "API de Nutrição", descrição, versão, segurança Bearer JWT |
| 11.2 | Seed de alimentos comuns | ❌ | `V2__seed_categorias_alimentos.sql` — +50 alimentos: arroz, feijão, frango, ovo, banana, etc. |
| 11.3 | Build final: `mvn clean package` | ❌ | Verificar empacotamento do JAR |
| 11.4 | Testes finais: `mvn clean test` | ❌ | Todos os testes passando com cobertura |
| 11.5 | Atualizar docs (ENDPOINTS.md, README.md) | ❌ | Refletir endpoints paginados, novos filtros, OpenAPI |

---
### Ciclo 12 — PR 4: Deploy em Producao (AWS EC2)
*Branch: `AN-08/ciclo-8-deploy-ajustes-finais` | Depende de: Ciclo 11*

| # | Tarefa | TDD? | Arquivos |
|---|--------|------|----------|
| 12.1 | Guia de deploy AWS EC2 (Nginx + Certbot + HTTPS) | ❌ | `docs/DEPLOY.md` |
| 12.2 | Ajustes finais na documentacao | ❌ | `docs/ENTIDADES.md`, `docs/ESTRUTURA.md`, `docs/ENDPOINTS.md`, `docs/PLANEJAMENTO.md`, `README.md` |
| 12.3 | Verificacao final: `mvn clean test` | ❌ | — |

**Resumo do Deploy**: A API foi hospedada em uma instancia EC2 (AWS) com Linux, usando Nginx como reverse proxy com dominio proprio e certificado SSL via Certbot (Let's Encrypt). O deploy manual ensinou conceitos de redes (security groups, firewall), configuracao de DNS, vinculacao de dominio e HTTPS — conhecimento fundamental antes da adocao de containers.

---

[← Voltar ao README principal](../README.md)
