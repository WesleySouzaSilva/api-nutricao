# api-nutricao

API REST para controle alimentar e nutricional. Projeto portfólio construído com TDD, Clean Architecture e práticas profissionais de desenvolvimento Spring.

**Stack:** Java 11 · Spring Boot 2.7.18 · MySQL 8 · Flyway · JWT (auth0) · SpringDoc OpenAPI · Docker · JUnit 5 + Mockito

---

## Quick Start

```bash
# Compilar
mvn clean compile

# Executar testes
mvn clean test

# Subir aplicação (requer Docker)
docker compose up --build
```

Acessar em `http://localhost:8080` · Swagger em `http://localhost:8080/swagger-ui.html`

---

## Entidades (9)

| Entidade | Descrição |
|---|---|
| **Usuario** | Cadastro e autenticação |
| **CategoriaAlimento** | Classificação de alimentos |
| **Alimento** | Tabela nutricional completa |
| **Refeicao** | Registro de refeições (CAFE_DA_MANHA, ALMOCO, JANTAR, LANCHE) |
| **AlimentoRefeicao** | Itens consumidos em cada refeição |
| **MetaNutricional** | Metas com periodicidade (DIARIO, SEMANAL, MENSAL, TRIMESTRAL) |
| **Objetivo** | Objetivo do usuário (GANHAR_MASSA, REDUZIR_GORDURA, MANTER_PESO) |
| **RegistroDiario** | Acompanhamento diário (peso, água, passos) |
| **AlimentoFavorito** | Alimentos favoritos do usuário |

Detalhes completos → [`docs/ENTIDADES.md`](docs/ENTIDADES.md)

---

## Documentação

| Documento | Conteúdo |
|---|---|
| [`docs/ENDPOINTS.md`](docs/ENDPOINTS.md) | Todos os endpoints da API (28 no total) |
| [`docs/PLANEJAMENTO.md`](docs/PLANEJAMENTO.md) | Abordagem TDD, ciclos de implementação e matriz de testes |
| [`docs/ESTRUTURA.md`](docs/ESTRUTURA.md) | Estrutura completa do projeto (~82 arquivos) |
| [`docs/HISTORICO.md`](docs/HISTORICO.md) | Histórico e contexto do projeto |

---

## Diferenciais

| Característica | api-nutricao | api-mbs (referência) |
|---|---|---|
| **Abordagem** | TDD (test-first) | Sem testes |
| **Migrações** | Flyway versionado | Manual |
| **Entidades** | 9, enxutas e focadas | 14+, com acoplamento excessivo |
| **Exception Handling** | RFC 7807 com handler global | RFC 7807 |
| **Documentação** | SpringDoc OpenAPI | SpringFox (deprecated) |
| **Container** | Docker + Compose ativo | Docker comentado |
| **Segurança** | JWT + BCrypt + env vars | Secrets hardcoded |

---

<div align="center">
  Projeto portfólio — <a href="https://github.com/WesleySouzaSilva/api-nutricao">github.com/WesleySouzaSilva/api-nutricao</a>
</div>
