# api-nutricao

API REST para controle alimentar e nutricional. Projeto portfólio construído com TDD, Clean Architecture e práticas profissionais de desenvolvimento Spring.

---

## Stack Tecnológica

| Categoria | Tecnologia | Versão |
|---|---|---|
| **Linguagem** | Java | 11 (LTS) |
| **Framework** | Spring Boot | 2.7.18 |
| **Build** | Maven | 3.9.9 |
| **Banco** | MySQL | 8.0+ |
| **Migration** | Flyway | 9.x |
| **Autenticação** | auth0 java-jwt | 4.4.0 |
| **Documentação** | SpringDoc OpenAPI | 1.8.0 |
| **Mapping** | ModelMapper | 3.2.1 |
| **Testes** | JUnit 5 + Mockito | — |
| **Container** | Docker + Compose | — |

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

## Documentação

Os detalhes do projeto foram organizados em documentos separados:

| Documento | Conteúdo |
|---|---|
| [`docs/ENTIDADES.md`](docs/ENTIDADES.md) | Diagrama ER, entidades do domínio e descrição dos campos |
| [`docs/ENDPOINTS.md`](docs/ENDPOINTS.md) | Todos os endpoints da API (28 endpoints, 9 controllers) |
| [`docs/PLANEJAMENTO.md`](docs/PLANEJAMENTO.md) | Abordagem TDD, matriz de testes e ciclos de implementação |
| [`docs/ESTRUTURA.md`](docs/ESTRUTURA.md) | Estrutura completa de arquivos (~82) e como executar |
| [`docs/HISTORICO.md`](docs/HISTORICO.md) | Histórico e trajetória de aprendizado |

---

## Diferenciais

| Característica | api-nutricao | api-mbs (referência) |
|---|---|---|
| **Abordagem TDD** | Testes antes da implementação (RED → GREEN → REFACTOR) | Sem testes automatizados |
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
