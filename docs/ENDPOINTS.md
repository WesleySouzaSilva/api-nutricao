# Endpoints da API

## Autenticação (`/v1/auth`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `POST` | `/v1/auth/register` | Cadastrar novo usuário | Aberto |
| `POST` | `/v1/auth/login` | Login (email + senha) → JWT | Aberto |

## Usuários (`/v1/usuarios`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/usuarios/me` | Perfil do usuário logado | JWT |
| `PUT` | `/v1/usuarios/me` | Atualizar perfil | JWT |
| `DELETE` | `/v1/usuarios/me` | Excluir conta | JWT |

## Categorias (`/v1/categorias`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/categorias` | Listar todas as categorias | JWT |
| `POST` | `/v1/categorias` | Criar nova categoria | JWT |
| `DELETE` | `/v1/categorias/{id}` | Remover categoria | JWT |

## Alimentos (`/v1/alimentos`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/alimentos` | Listar (paginado, filtro por nome/categoria) | JWT |
| `GET` | `/v1/alimentos/{id}` | Detalhe completo com informação nutricional | JWT |
| `POST` | `/v1/alimentos` | Cadastrar novo alimento | JWT |
| `PUT` | `/v1/alimentos/{id}` | Atualizar alimento | JWT |
| `DELETE` | `/v1/alimentos/{id}` | Remover alimento | JWT |
| `GET` | `/v1/alimentos/favoritos` | Listar alimentos favoritos do usuário | JWT |
| `POST` | `/v1/alimentos/{id}/favoritar` | Favoritar/desfavoritar alimento | JWT |

## Refeições (`/v1/refeicoes`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/refeicoes` | Listar refeições do usuário (filtro por data) | JWT |
| `GET` | `/v1/refeicoes/{id}` | Detalhe da refeição com alimentos consumidos | JWT |
| `POST` | `/v1/refeicoes` | Registrar refeição com lista de alimentos + quantidades | JWT |
| `PUT` | `/v1/refeicoes/{id}` | Atualizar refeição | JWT |
| `DELETE` | `/v1/refeicoes/{id}` | Remover refeição | JWT |
| `GET` | `/v1/refeicoes/resumo/diario` | Resumo nutricional do dia (totais + por refeição) | JWT |
| `GET` | `/v1/refeicoes/resumo/semanal` | Resumo nutricional dos últimos 7 dias | JWT |

## Metas Nutricionais (`/v1/metas`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/metas` | Listar metas do usuário | JWT |
| `POST` | `/v1/metas` | Criar meta (calorias, proteína, carboidrato, gordura + periodicidade) | JWT |
| `PUT` | `/v1/metas/{id}` | Atualizar meta (inclusive periodicidade) | JWT |
| `DELETE` | `/v1/metas/{id}` | Remover meta | JWT |

## Objetivos (`/v1/objetivos`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/objetivos` | Listar objetivos do usuário | JWT |
| `GET` | `/v1/objetivos/ativo` | Objetivo ativo atual | JWT |
| `POST` | `/v1/objetivos` | Criar objetivo (ganhar massa, reduzir gordura, manter peso) | JWT |
| `PUT` | `/v1/objetivos/{id}` | Atualizar objetivo | JWT |
| `DELETE` | `/v1/objetivos/{id}` | Remover objetivo | JWT |

## Registro Diário (`/v1/registros`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `POST` | `/v1/registros/diario` | Registrar peso/água/passos do dia | JWT |
| `GET` | `/v1/registros/diario/hoje` | Último registro do dia | JWT |
| `GET` | `/v1/registros/diario/historico` | Histórico por período | JWT |

## Dashboard (`/v1/dashboard`)

> **Nota**: Endpoints de dashboard são referência para consumo pelo frontend. Não serão implementados neste projeto — o frontend deve consumir os endpoints de Metas, Registro Diário e Refeições para montar seus próprios dashboards.

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/dashboard/hoje` | Progresso de hoje vs metas (calorias consumidas, água, passos) | JWT |
| `GET` | `/v1/dashboard/semanal` | Evolução dos últimos 7 dias (gráfico de consumo vs meta) | JWT |

## Health Check

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `GET` | `/v1/health` | Health check da aplicação | Aberto |

---

[← Voltar ao README principal](../README.md)
