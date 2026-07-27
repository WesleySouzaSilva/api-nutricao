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
│   │   │   │   └── OpenApiConfig.java
│   │   │   ├── domain/
│   │   │   │   ├── model/
│   │   │   │   │   ├── Usuario.java
│   │   │   │   │   ├── CategoriaAlimento.java
│   │   │   │   │   ├── Alimento.java
│   │   │   │   │   ├── Refeicao.java
│   │   │   │   │   ├── AlimentoRefeicao.java
│   │   │   │   │   ├── MetaNutricional.java
│   │   │   │   │   ├── Objetivo.java
│   │   │   │   │   ├── RegistroDiario.java
│   │   │   │   │   └── AlimentoFavorito.java
│   │   │   │   ├── repository/
│   │   │   │   │   ├── UsuarioRepository.java
│   │   │   │   │   ├── AlimentoRepository.java
│   │   │   │   │   ├── RefeicaoRepository.java
│   │   │   │   │   ├── MetaNutricionalRepository.java
│   │   │   │   │   ├── ObjetivoRepository.java
│   │   │   │   │   └── RegistroDiarioRepository.java
│   │   │   │   └── service/
│   │   │   │       ├── UsuarioService.java
│   │   │   │       ├── AlimentoService.java
│   │   │   │       ├── RefeicaoService.java
│   │   │   │       ├── MetaNutricionalService.java
│   │   │   │       ├── ObjetivoService.java
│   │   │   │       └── RegistroDiarioService.java
│   │   │   ├── api/
│   │   │   │   ├── controller/
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── UsuarioController.java
│   │   │   │   │   ├── CategoriaController.java
│   │   │   │   │   ├── AlimentoController.java
│   │   │   │   │   ├── RefeicaoController.java
│   │   │   │   │   ├── MetaNutricionalController.java
│   │   │   │   │   ├── ObjetivoController.java
│   │   │   │   │   ├── RegistroDiarioController.java
│   │   │   │   │   └── HealthController.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── request/
│   │   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   │   ├── UsuarioCreateRequest.java
│   │   │   │   │   │   ├── UsuarioUpdateRequest.java
│   │   │   │   │   │   ├── CategoriaRequest.java
│   │   │   │   │   │   ├── AlimentoRequest.java
│   │   │   │   │   │   ├── RefeicaoRequest.java
│   │   │   │   │   │   ├── MetaRequest.java
│   │   │   │   │   │   ├── ObjetivoRequest.java
│   │   │   │   │   │   └── RegistroDiarioRequest.java
│   │   │   │   │   └── response/
│   │   │   │   │       ├── TokenResponse.java
│   │   │   │   │       ├── UsuarioResponse.java
│   │   │   │   │       ├── AlimentoResponse.java
│   │   │   │   │       ├── RefeicaoResponse.java
│   │   │   │   │       ├── MetaResponse.java
│   │   │   │   │       ├── ObjetivoResponse.java
│   │   │   │   │       ├── ResumoDiarioResponse.java
│   │   │   │   │       └── RegistroDiarioResponse.java
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
│       │   ├── UsuarioTest.java
│       │   ├── CategoriaAlimentoTest.java
│       │   ├── AlimentoTest.java
│       │   ├── RefeicaoTest.java
│       │   ├── AlimentoRefeicaoTest.java
│       │   ├── MetaNutricionalTest.java
│       │   ├── ObjetivoTest.java
│       │   ├── RegistroDiarioTest.java
│       │   └── AlimentoFavoritoTest.java
│       ├── domain/repository/
│       │   ├── UsuarioRepositoryTest.java
│       │   ├── AlimentoRepositoryTest.java
│       │   ├── RefeicaoRepositoryTest.java
│       │   ├── MetaNutricionalRepositoryTest.java
│       │   ├── ObjetivoRepositoryTest.java
│       │   └── RegistroDiarioRepositoryTest.java
│       ├── domain/service/
│       │   ├── UsuarioServiceTest.java
│       │   ├── AlimentoServiceTest.java
│       │   ├── RefeicaoServiceTest.java
│       │   ├── MetaNutricionalServiceTest.java
│       │   ├── ObjetivoServiceTest.java
│       │   └── RegistroDiarioServiceTest.java
│       ├── api/controller/
│       │   ├── AuthControllerTest.java
│       │   ├── UsuarioControllerTest.java
│       │   ├── CategoriaControllerTest.java
│       │   ├── AlimentoControllerTest.java
│       │   ├── RefeicaoControllerTest.java
│       │   ├── MetaNutricionalControllerTest.java
│       │   ├── ObjetivoControllerTest.java
│       │   └── RegistroDiarioControllerTest.java
│       └── security/
│           ├── JwtUtilTest.java
│           └── JwtAuthenticationFilterTest.java
```

**Total estimado**: ~82 arquivos.

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
