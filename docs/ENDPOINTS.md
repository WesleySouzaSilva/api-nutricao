# Endpoints da API

> **Swagger UI**: `http://localhost:8080/swagger-ui/index.html` — documentacao interativa OpenAPI 3.0

## Autenticacao (`/api/v1/auth`)

| Metodo | Rota | Descricao | Auth |
|--------|------|-----------|------|
| `POST` | `/api/v1/auth/login` | Login (email + senha) -> JWT | Aberto |
| `POST` | `/api/v1/auth/login/token_id` | Validar token JWT | Aberto |

## Usuarios (`/api/v1/usuarios`)

| Metodo | Rota | Descricao | Auth |
|--------|------|-----------|------|
| `POST` | `/api/v1/usuarios` | Cadastrar novo usuario | Aberto |
| `GET` | `/api/v1/usuarios` | Listar todos os usuarios | JWT |
| `GET` | `/api/v1/usuarios/{id}` | Buscar usuario por ID | JWT |
| `GET` | `/api/v1/usuarios/email/{email}` | Buscar usuario por email | JWT |
| `PUT` | `/api/v1/usuarios/{id}` | Atualizar usuario | JWT |
| `DELETE` | `/api/v1/usuarios/{id}` | Excluir usuario | JWT |

## Categorias de Alimentos (`/api/v1/categorias`)

| Metodo | Rota | Descricao | Auth |
|--------|------|-----------|------|
| `GET` | `/api/v1/categorias` | Listar categorias (paginado) | JWT |
| `GET` | `/api/v1/categorias/{id}` | Buscar categoria por ID | JWT |
| `POST` | `/api/v1/categorias` | Criar nova categoria | JWT |
| `PUT` | `/api/v1/categorias/{id}` | Atualizar categoria | JWT |
| `DELETE` | `/api/v1/categorias/{id}` | Remover categoria | JWT |

## Alimentos (`/api/v1/alimentos`)

| Metodo | Rota | Descricao | Auth |
|--------|------|-----------|------|
| `GET` | `/api/v1/alimentos` | Listar (paginado, filtrar por nome/categoria) | JWT |
| `GET` | `/api/v1/alimentos/{id}` | Buscar alimento por ID com info nutricional | JWT |
| `POST` | `/api/v1/alimentos` | Cadastrar novo alimento | JWT |
| `PUT` | `/api/v1/alimentos/{id}` | Atualizar alimento | JWT |
| `DELETE` | `/api/v1/alimentos/{id}` | Remover alimento | JWT |

### Parametros de filtro (alimentos)
| Parametro | Tipo | Descricao |
|-----------|------|------------|
| `nome` | String | Filtro por nome (LIKE, case-insensitive) |
| `categoriaAlimento` | Integer | Filtro por ID da categoria |
| `page` | Integer | Numero da pagina (0-based) |
| `size` | Integer | Tamanho da pagina (default: 10) |
| `sort` | String | Ordenacao (ex: `nome,asc`) |

## Alimentos Favoritos (`/api/v1/favoritos`)

| Metodo | Rota | Descricao | Auth |
|--------|------|-----------|------|
| `POST` | `/api/v1/favoritos` | Adicionar alimento aos favoritos | JWT |
| `GET` | `/api/v1/favoritos/usuario/{usuarioId}` | Listar favoritos do usuario | JWT |
| `DELETE` | `/api/v1/favoritos/usuario/{usuarioId}/alimento/{alimentoId}` | Remover dos favoritos | JWT |

## Refeicoes (`/api/v1/refeicoes`)

| Metodo | Rota | Descricao | Auth |
|--------|------|-----------|------|
| `GET` | `/api/v1/refeicoes` | Listar refeicoes (paginado, filtrar por usuario/data) | JWT |
| `GET` | `/api/v1/refeicoes/{id}` | Buscar refeicao por ID | JWT |
| `POST` | `/api/v1/refeicoes` | Criar nova refeicao | JWT |
| `PUT` | `/api/v1/refeicoes/{id}` | Atualizar refeicao | JWT |
| `DELETE` | `/api/v1/refeicoes/{id}` | Remover refeicao | JWT |

### Parametros de filtro (refeicoes)
| Parametro | Tipo | Descricao |
|-----------|------|------------|
| `usuarioId` | Integer | Filtro por ID do usuario |
| `dataInicio` | LocalDate | Data inicial (ISO: yyyy-MM-dd) |
| `dataFim` | LocalDate | Data final (ISO: yyyy-MM-dd) |
| `page` | Integer | Numero da pagina (0-based) |
| `size` | Integer | Tamanho da pagina (default: 10) |
| `sort` | String | Ordenacao (ex: `dataHora,desc`) |

## Alimentos da Refeicao (`/api/v1/alimentos-refeicao`)

| Metodo | Rota | Descricao | Auth |
|--------|------|-----------|------|
| `POST` | `/api/v1/alimentos-refeicao` | Adicionar alimento a uma refeicao | JWT |
| `GET` | `/api/v1/alimentos-refeicao/refeicao/{refeicaoId}` | Listar alimentos de uma refeicao | JWT |
| `DELETE` | `/api/v1/alimentos-refeicao/{id}` | Remover alimento da refeicao | JWT |
| `DELETE` | `/api/v1/alimentos-refeicao/refeicao/{refeicaoId}` | Remover todos os alimentos de uma refeicao | JWT |

## Metas Nutricionais (`/api/v1/metas-nutricionais`)

| Metodo | Rota | Descricao | Auth |
|--------|------|-----------|------|
| `GET` | `/api/v1/metas-nutricionais` | Listar metas (paginado, filtrar por usuario/data) | JWT |
| `GET` | `/api/v1/metas-nutricionais/{id}` | Buscar meta por ID | JWT |
| `GET` | `/api/v1/metas-nutricionais/usuario/{usuarioId}` | Listar metas de um usuario | JWT |
| `GET` | `/api/v1/metas-nutricionais/usuario/{usuarioId}/ultima` | Buscar ultima meta do usuario | JWT |
| `POST` | `/api/v1/metas-nutricionais` | Criar nova meta nutricional | JWT |
| `PUT` | `/api/v1/metas-nutricionais/{id}` | Atualizar meta | JWT |
| `DELETE` | `/api/v1/metas-nutricionais/{id}` | Remover meta | JWT |

### Parametros de filtro (metas nutricionais)
| Parametro | Tipo | Descricao |
|-----------|------|------------|
| `usuarioId` | Integer | Filtro por ID do usuario |
| `dataInicio` | LocalDate | Data inicial (ISO: yyyy-MM-dd) |
| `dataFim` | LocalDate | Data final (ISO: yyyy-MM-dd) |
| `page` | Integer | Numero da pagina (0-based) |
| `size` | Integer | Tamanho da pagina (default: 10) |
| `sort` | String | Ordenacao |

## Objetivos (`/api/v1/objetivos`)

| Metodo | Rota | Descricao | Auth |
|--------|------|-----------|------|
| `GET` | `/api/v1/objetivos` | Listar objetivos (paginado, filtrar por usuario/tipo/data) | JWT |
| `GET` | `/api/v1/objetivos/{id}` | Buscar objetivo por ID | JWT |
| `GET` | `/api/v1/objetivos/usuario/{usuarioId}` | Listar objetivos de um usuario | JWT |
| `POST` | `/api/v1/objetivos` | Criar novo objetivo | JWT |
| `PUT` | `/api/v1/objetivos/{id}` | Atualizar objetivo | JWT |
| `DELETE` | `/api/v1/objetivos/{id}` | Remover objetivo | JWT |

### Parametros de filtro (objetivos)
| Parametro | Tipo | Descricao |
|-----------|------|------------|
| `usuarioId` | Integer | Filtro por ID do usuario |
| `tipo` | String | Tipo do objetivo |
| `dataInicio` | LocalDate | Data inicial (ISO: yyyy-MM-dd) |
| `dataFim` | LocalDate | Data final (ISO: yyyy-MM-dd) |
| `page` | Integer | Numero da pagina (0-based) |
| `size` | Integer | Tamanho da pagina (default: 10) |
| `sort` | String | Ordenacao |

## Registros Diarios (`/api/v1/registros-diarios`)

| Metodo | Rota | Descricao | Auth |
|--------|------|-----------|------|
| `GET` | `/api/v1/registros-diarios` | Listar registros (paginado, filtrar por usuario/data) | JWT |
| `GET` | `/api/v1/registros-diarios/{id}` | Buscar registro por ID | JWT |
| `POST` | `/api/v1/registros-diarios` | Criar novo registro diario | JWT |
| `PUT` | `/api/v1/registros-diarios/{id}` | Atualizar registro | JWT |
| `DELETE` | `/api/v1/registros-diarios/{id}` | Remover registro | JWT |

### Parametros de filtro (registros diarios)
| Parametro | Tipo | Descricao |
|-----------|------|------------|
| `usuarioId` | Integer | Filtro por ID do usuario |
| `dataInicio` | LocalDate | Data inicial (ISO: yyyy-MM-dd) |
| `dataFim` | LocalDate | Data final (ISO: yyyy-MM-dd) |
| `page` | Integer | Numero da pagina (0-based) |
| `size` | Integer | Tamanho da pagina (default: 10) |
| `sort` | String | Ordenacao (ex: `dataRegistro,desc`) |

---

[<- Voltar ao README principal](../README.md)
