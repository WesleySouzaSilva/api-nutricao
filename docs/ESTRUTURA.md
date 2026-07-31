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
│   ├── PLANEJAMENTO.md
│   └── DEPLOY.md
├── src/
│   ├── main/
│   │   ├── java/br/com/nutricao/
│   │   │   ├── ApiNutricaoApplication.java
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── UsuarioController.java
│   │   │   │   ├── CategoriaAlimentoController.java
│   │   │   │   ├── AlimentoController.java
│   │   │   │   ├── RefeicaoController.java
│   │   │   │   ├── AlimentoRefeicaoController.java
│   │   │   │   ├── AlimentoFavoritoController.java
│   │   │   │   ├── MetaNutricionalController.java
│   │   │   │   ├── ObjetivoController.java
│   │   │   │   ├── RegistroDiarioController.java
│   │   │   │   └── documentacao/
│   │   │   │       ├── AuthControllerSwagger.java
│   │   │   │       ├── UsuarioControllerSwagger.java
│   │   │   │       ├── CategoriaAlimentoControllerSwagger.java
│   │   │   │       ├── AlimentoControllerSwagger.java
│   │   │   │       ├── RefeicaoControllerSwagger.java
│   │   │   │       ├── AlimentoRefeicaoControllerSwagger.java
│   │   │   │       ├── AlimentoFavoritoControllerSwagger.java
│   │   │   │       ├── MetaNutricionalControllerSwagger.java
│   │   │   │       ├── ObjetivoControllerSwagger.java
│   │   │   │       └── RegistroDiarioControllerSwagger.java
│   │   │   ├── domain/
│   │   │   │   ├── Usuario.java
│   │   │   │   ├── CategoriaAlimento.java
│   │   │   │   ├── Alimento.java
│   │   │   │   ├── Refeicao.java
│   │   │   │   ├── AlimentoRefeicao.java
│   │   │   │   ├── AlimentoFavorito.java
│   │   │   │   ├── MetaNutricional.java
│   │   │   │   ├── Objetivo.java
│   │   │   │   ├── RegistroDiario.java
│   │   │   │   └── dto/
│   │   │   │       ├── insercao/
│   │   │   │       │   ├── UsuarioRequest.java
│   │   │   │       │   ├── CategoriaAlimentoRequest.java
│   │   │   │       │   ├── AlimentoRequest.java
│   │   │   │       │   ├── RefeicaoRequest.java
│   │   │   │       │   ├── AlimentoRefeicaoRequest.java
│   │   │   │       │   ├── AlimentoFavoritoRequest.java
│   │   │   │       │   ├── MetaNutricionalRequest.java
│   │   │   │       │   ├── ObjetivoRequest.java
│   │   │   │       │   └── RegistroDiarioRequest.java
│   │   │   │       ├── visualizacao/
│   │   │   │       │   ├── UsuarioResponse.java
│   │   │   │       │   ├── CategoriaAlimentoResponse.java
│   │   │   │       │   ├── AlimentoResponse.java
│   │   │   │       │   ├── RefeicaoResponse.java
│   │   │   │       │   ├── AlimentoRefeicaoResponse.java
│   │   │   │       │   ├── AlimentoFavoritoResponse.java
│   │   │   │       │   ├── MetaNutricionalResponse.java
│   │   │   │       │   ├── ObjetivoResponse.java
│   │   │   │       │   ├── RegistroDiarioResponse.java
│   │   │   │       │   ├── Login.java
│   │   │   │       │   ├── LoginToken.java
│   │   │   │       │   └── AuthResponseDTO.java
│   │   │   │       └── filtro/
│   │   │   │           ├── AlimentoCamposFiltro.java
│   │   │   │           ├── MetaNutricionalCamposFiltro.java
│   │   │   │           ├── ObjetivoCamposFiltro.java
│   │   │   │           ├── RefeicaoCamposFiltro.java
│   │   │   │           └── RegistroDiarioCamposFiltro.java
│   │   │   ├── repositories/
│   │   │   │   ├── UsuarioRepository.java
│   │   │   │   ├── CategoriaAlimentoRepository.java
│   │   │   │   ├── AlimentoRepository.java
│   │   │   │   ├── RefeicaoRepository.java
│   │   │   │   ├── AlimentoRefeicaoRepository.java
│   │   │   │   ├── AlimentoFavoritoRepository.java
│   │   │   │   ├── MetaNutricionalRepository.java
│   │   │   │   ├── ObjetivoRepository.java
│   │   │   │   └── RegistroDiarioRepository.java
│   │   │   ├── specification/
│   │   │   │   ├── AlimentoFiltro.java
│   │   │   │   ├── MetaNutricionalFiltro.java
│   │   │   │   ├── ObjetivoFiltro.java
│   │   │   │   ├── RefeicaoFiltro.java
│   │   │   │   └── RegistroDiarioFiltro.java
│   │   │   ├── security/
│   │   │   │   ├── ConfiguracaoSecurity.java
│   │   │   │   ├── JWTUtil.java
│   │   │   │   ├── FiltroSecurity.java
│   │   │   │   ├── UsuarioDetailsService.java
│   │   │   │   └── UsuarioDetails.java
│   │   │   ├── services/
│   │   │   │   ├── UsuarioService.java
│   │   │   │   ├── CategoriaAlimentoService.java
│   │   │   │   ├── AlimentoService.java
│   │   │   │   ├── RefeicaoService.java
│   │   │   │   ├── AlimentoRefeicaoService.java
│   │   │   │   ├── AlimentoFavoritoService.java
│   │   │   │   ├── MetaNutricionalService.java
│   │   │   │   ├── ObjetivoService.java
│   │   │   │   ├── RegistroDiarioService.java
│   │   │   │   └── exception/
│   │   │   │       ├── ApiExceptionHandler.java
│   │   │   │       ├── NegocioException.java
│   │   │   │       ├── Problema.java
│   │   │   │       ├── TipoProblema.java
│   │   │   │       └── entidades/
│   │   │   │           ├── EntidadeNaoEncontradaException.java
│   │   │   │           └── EntidadeEmUsoException.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── application-prod.properties
│   │       ├── application-test.properties
│   │       └── db/migration/
│   │           ├── V1__create_tables.sql
│   │           └── V2__seed_categorias_alimentos.sql
│   └── test/java/br/com/nutricao/
│       ├── controller/
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
│       ├── domain/
│       │   ├── UsuarioTest.java
│       │   ├── CategoriaAlimentoTest.java
│       │   ├── AlimentoTest.java
│       │   ├── RefeicaoTest.java
│       │   ├── AlimentoRefeicaoTest.java
│       │   ├── AlimentoFavoritoTest.java
│       │   ├── MetaNutricionalTest.java
│       │   ├── ObjetivoTest.java
│       │   └── RegistroDiarioTest.java
│       ├── integration/
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
│       ├── repositories/
│       │   ├── UsuarioRepositoryTest.java
│       │   ├── CategoriaAlimentoRepositoryTest.java
│       │   ├── AlimentoRepositoryTest.java
│       │   ├── RefeicaoRepositoryTest.java
│       │   ├── AlimentoRefeicaoRepositoryTest.java
│       │   ├── AlimentoFavoritoRepositoryTest.java
│       │   ├── MetaNutricionalRepositoryTest.java
│       │   ├── ObjetivoRepositoryTest.java
│       │   └── RegistroDiarioRepositoryTest.java
│       ├── security/
│       │   └── JWTUtilTest.java
│       ├── services/
│       │   ├── UsuarioServiceTest.java
│       │   ├── CategoriaAlimentoServiceTest.java
│       │   ├── AlimentoServiceTest.java
│       │   ├── RefeicaoServiceTest.java
│       │   ├── AlimentoRefeicaoServiceTest.java
│       │   ├── AlimentoFavoritoServiceTest.java
│       │   ├── MetaNutricionalServiceTest.java
│       │   ├── ObjetivoServiceTest.java
│       │   ├── RegistroDiarioServiceTest.java
│       │   └── exception/
│       │       ├── ApiExceptionHandlerTest.java
│       │       ├── NegocioExceptionTest.java
│       │       ├── ProblemaTest.java
│       │       ├── TipoProblemaTest.java
│       │       └── entidades/
│       │           ├── EntidadeNaoEncontradaExceptionTest.java
│       │           └── EntidadeEmUsoExceptionTest.java
```

**Total**: 145 arquivos Java (91 main + 54 test) | 331 testes

---

## Como Executar

### Pre-requisitos

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

# Subir aplicacao (requer MySQL local ou Docker)
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Producao (Docker Compose)

```bash
docker compose up --build
```

A API estara disponivel em `http://localhost:8080/api/v1/...` e o Swagger em `http://localhost:8080/swagger-ui/index.html`.

### Deploy em Producao

Consulte [`docs/DEPLOY.md`](DEPLOY.md) para o guia completo de deploy em AWS EC2 com Nginx + Certbot + HTTPS.

---

## Padroes de Commit e Branch

Seguimos o padrao descrito em [`padrao estrutura github.txt`](../padrao%20estrutura%20github.txt):

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
tipo(escopo): descricao

Tipos: feat, fix, docs, style, refactor, perf, test, chore
Exemplo: feat(alimento): criar CRUD de alimentos com filtros
```

### Pull Requests

```
[AN-{id}] tipo(escopo): descricao

Exemplo:
[AN-01] docs(projeto): criar documentacao e planejamento inicial
```

---

[← Voltar ao README principal](../README.md)
