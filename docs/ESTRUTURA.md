# Estrutura do Projeto

```
api-nutricao/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── .gitignore
├── .editorconfig
├── padrao estrutura github.txt
├── README.md
├── docs/
│   ├── HISTORICO.md
│   ├── ENTIDADES.md
│   ├── ENDPOINTS.md
│   ├── ESTRUTURA.md
│   └── PLANEJAMENTO.md
├── src/
│   ├── main/
│   │   ├── java/br/com/nutricao/
│   │   │   ├── ApiNutricaoApplication.java
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── JwtUtil.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   ├── UserDetailsServiceImpl.java
│   │   │   │   └── UsuarioDetails.java
│   │   │   ├── domain/
│   │   │   │   └── model/
│   │   │   │       ├── Usuario.java
│   │   │   │       ├── CategoriaAlimento.java
│   │   │   │       ├── Alimento.java
│   │   │   │       ├── Refeicao.java
│   │   │   │       ├── AlimentoRefeicao.java
│   │   │   │       ├── AlimentoFavorito.java
│   │   │   │       ├── MetaNutricional.java
│   │   │   │       ├── Objetivo.java
│   │   │   │       └── RegistroDiario.java
│   │   │   ├── infrastructure/
│   │   │   │   └── persistence/
│   │   │   │       ├── UsuarioRepository.java
│   │   │   │       ├── CategoriaAlimentoRepository.java
│   │   │   │       ├── AlimentoRepository.java
│   │   │   │       ├── RefeicaoRepository.java
│   │   │   │       ├── AlimentoRefeicaoRepository.java
│   │   │   │       ├── AlimentoFavoritoRepository.java
│   │   │   │       ├── MetaNutricionalRepository.java
│   │   │   │       ├── ObjetivoRepository.java
│   │   │   │       └── RegistroDiarioRepository.java
│   │   │   ├── application/
│   │   │   │   ├── dto/
│   │   │   │   │   ├── UsuarioRequest.java
│   │   │   │   │   ├── UsuarioResponse.java
│   │   │   │   │   ├── CategoriaAlimentoRequest.java
│   │   │   │   │   ├── CategoriaAlimentoResponse.java
│   │   │   │   │   ├── AlimentoRequest.java
│   │   │   │   │   ├── AlimentoResponse.java
│   │   │   │   │   ├── RefeicaoRequest.java
│   │   │   │   │   ├── RefeicaoResponse.java
│   │   │   │   │   ├── AlimentoRefeicaoRequest.java
│   │   │   │   │   ├── AlimentoRefeicaoResponse.java
│   │   │   │   │   ├── AlimentoFavoritoRequest.java
│   │   │   │   │   ├── AlimentoFavoritoResponse.java
│   │   │   │   │   ├── MetaNutricionalRequest.java
│   │   │   │   │   ├── MetaNutricionalResponse.java
│   │   │   │   │   ├── ObjetivoRequest.java
│   │   │   │   │   ├── ObjetivoResponse.java
│   │   │   │   │   ├── RegistroDiarioRequest.java
│   │   │   │   │   ├── RegistroDiarioResponse.java
│   │   │   │   │   ├── AuthRequest.java
│   │   │   │   │   ├── AuthResponse.java
│   │   │   │   │   └── LoginToken.java
│   │   │   │   └── service/
│   │   │   │       ├── UsuarioService.java
│   │   │   │       ├── CategoriaAlimentoService.java
│   │   │   │       ├── AlimentoService.java
│   │   │   │       ├── RefeicaoService.java
│   │   │   │       ├── AlimentoRefeicaoService.java
│   │   │   │       ├── AlimentoFavoritoService.java
│   │   │   │       ├── MetaNutricionalService.java
│   │   │   │       ├── ObjetivoService.java
│   │   │   │       └── RegistroDiarioService.java
│   │   │   ├── api/
│   │   │   │   ├── controller/
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── UsuarioController.java
│   │   │   │   │   ├── CategoriaAlimentoController.java
│   │   │   │   │   ├── AlimentoController.java
│   │   │   │   │   ├── RefeicaoController.java
│   │   │   │   │   ├── AlimentoRefeicaoController.java
│   │   │   │   │   ├── AlimentoFavoritoController.java
│   │   │   │   │   ├── MetaNutricionalController.java
│   │   │   │   │   ├── ObjetivoController.java
│   │   │   │   │   └── RegistroDiarioController.java
│   │   │   │   ├── exception/
│   │   │   │   │   ├── NegocioException.java
│   │   │   │   │   ├── EntidadeNaoEncontradaException.java
│   │   │   │   │   ├── EntidadeEmUsoException.java
│   │   │   │   │   ├── Problema.java
│   │   │   │   │   └── TipoProblema.java
│   │   │   │   └── handler/
│   │   │   │       └── ApiExceptionHandler.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── application-prod.properties
│   │       ├── application-test.properties
│   │       └── db/migration/
│   │           └── V1__create_tables.sql
│   └── test/java/br/com/nutricao/
│       ├── domain/model/
│       │   ├── UsuarioTest.java
│       │   ├── CategoriaAlimentoTest.java
│       │   ├── AlimentoTest.java
│       │   ├── RefeicaoTest.java
│       │   ├── AlimentoRefeicaoTest.java
│       │   ├── AlimentoFavoritoTest.java
│       │   ├── MetaNutricionalTest.java
│       │   ├── ObjetivoTest.java
│       │   └── RegistroDiarioTest.java
│       ├── infrastructure/persistence/
│       │   ├── UsuarioRepositoryTest.java
│       │   ├── CategoriaAlimentoRepositoryTest.java
│       │   ├── AlimentoRepositoryTest.java
│       │   ├── RefeicaoRepositoryTest.java
│       │   ├── AlimentoRefeicaoRepositoryTest.java
│       │   ├── AlimentoFavoritoRepositoryTest.java
│       │   ├── MetaNutricionalRepositoryTest.java
│       │   ├── ObjetivoRepositoryTest.java
│       │   └── RegistroDiarioRepositoryTest.java
│       ├── application/service/
│       │   ├── UsuarioServiceTest.java
│       │   ├── CategoriaAlimentoServiceTest.java
│       │   ├── AlimentoServiceTest.java
│       │   ├── RefeicaoServiceTest.java
│       │   ├── AlimentoRefeicaoServiceTest.java
│       │   ├── AlimentoFavoritoServiceTest.java
│       │   ├── MetaNutricionalServiceTest.java
│       │   ├── ObjetivoServiceTest.java
│       │   └── RegistroDiarioServiceTest.java
│       ├── api/controller/
│       │   ├── AuthControllerTest.java
│       │   ├── UsuarioControllerTest.java
│       │   ├── CategoriaAlimentoControllerTest.java
│       │   ├── AlimentoControllerTest.java
│       │   ├── RefeicaoControllerTest.java
│       │   ├── AlimentoRefeicaoControllerTest.java
│       │   ├── AlimentoFavoritoControllerTest.java
│       │   ├── MetaNutricionalControllerTest.java
│       │   ├── ObjetivoControllerTest.java
│       │   └── RegistroDiarioControllerTest.java
│       ├── api/integration/
│       │   ├── IntegrationTestConfig.java
│       │   ├── UsuarioIntegrationTest.java
│       │   ├── CategoriaAlimentoIntegrationTest.java
│       │   ├── AlimentoIntegrationTest.java
│       │   ├── RefeicaoIntegrationTest.java
│       │   ├── AlimentoRefeicaoIntegrationTest.java
│       │   ├── AlimentoFavoritoIntegrationTest.java
│       │   ├── MetaNutricionalIntegrationTest.java
│       │   ├── ObjetivoIntegrationTest.java
│       │   └── RegistroDiarioIntegrationTest.java
│       ├── api/exception/
│       │   ├── NegocioExceptionTest.java
│       │   ├── EntidadeNaoEncontradaExceptionTest.java
│       │   ├── EntidadeEmUsoExceptionTest.java
│       │   ├── ProblemaTest.java
│       │   └── TipoProblemaTest.java
│       ├── api/handler/
│       │   └── ApiExceptionHandlerTest.java
│       └── config/
│           └── JwtUtilTest.java
```

**Total real**: ~107 arquivos Java (69 main + 54 test).

---

## Como Executar

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

## Padrões de Commit e Branch

Seguimos o padrão descrito em [`padrao estrutura github.txt`](../padrao%20estrutura%20github.txt):

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

[← Voltar ao README principal](../README.md)
