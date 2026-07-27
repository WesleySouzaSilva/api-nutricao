# Entidades do Domínio

## Diagrama ER

```
┌──────────────────┐     ┌──────────────────┐     ┌───────────────────┐     ┌──────────────────┐
│    Usuario       │1──N│    Refeicao      │1──N│ AlimentoRefeicao  │N──1│    Alimento      │
├──────────────────┤     ├──────────────────┤     ├───────────────────┤     ├──────────────────┤
│ id (PK)          │     │ id (PK)          │     │ id (PK)           │     │ id (PK)          │
│ nome             │     │ usuario_id (FK)  │     │ refeicao_id (FK)  │     │ nome             │
│ senha            │     │ tipo             │     │ alimento_id (FK)  │     │ categoria_id (FK)│──┐
│ email            │     │ data_refeicao    │     │ quantidade        │     │ categoria        │  │
│ data_nascimento  │     │ observacao       │     │ calorias (calc)   │     │ calorias         │  │
│ altura           │     │ created_at       │     │ created_at        │     │ proteina         │  │
│ sexo             │     └──────────────────┘     └───────────────────┘     │ carboidrato      │  │
│ data_cadastro    │                                                         │ gordura          │  │
│ medida           │                                                         │ fibra            │  │
│ tipo_login       │                                                         └──────────────────┘  │
│ token_id         │                                                                              │
└────────┬─────────┘                                                                              │
         │1                                                                                        │
         │                                                                                         │
         │1              ┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐     │
         ├──────────────N│  MetaNutricional │     │ RegistroDiario   │     │  CategoriaAlim   │─────┘
         │               ├──────────────────┤     ├──────────────────┤     ├──────────────────┤
         │               │ id (PK)          │     │ id (PK)          │     │ id (PK)          │
         │               │ usuario_id (FK)  │     │ usuario_id (FK)  │     │ nome             │
         │               │ tipo (CALORIA/   │     │ data             │     │ created_at       │
         │               │   PROTEINA/      │     │ peso_jejum       │     └──────────────────┘
         │               │   CARBOIDRATO/   │     │ agua_ml          │
         │               │   GORDURA)       │     │ passos           │
         │               │ valor_meta       │     │ created_at       │
         │               │ periodicidade    │     └──────────────────┘
         │               │   (DIARIO/       │
         │               │    SEMANAL/      │
         │               │    MENSAL/       │
         │               │    TRIMESTRAL)   │
         │               │ data_inicio      │
         │               │ data_fim         │
         │               │ created_at       │
         │               └──────────────────┘
         │
         │1              ┌────────────────────────┐
         ├──────────────N│      Objetivo           │
         │               ├────────────────────────┤
         │               │ id (PK)                │
         │               │ usuario_id (FK)        │
         │               │ tipo (GANHAR_MASSA/     │
         │               │   REDUZIR_GORDURA/      │
         │               │   MANTER_PESO)          │
         │               │ data_inicio             │
         │               │ data_fim                │
         │               │ ativo                   │
         │               │ created_at              │
         │               └────────────────────────┘
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

## Descrição das Entidades

| Entidade | Tabela | Finalidade | Campos principais |
|---|---|---|---|
| **Usuario** | `usuario` | Cadastro e autenticação de usuários | id, nome, senha, email, dataNascimento, altura, sexo, dataCadastro, medida, tipoLogin, tokenId |
| **CategoriaAlimento** | `categoria_alimento` | Classificação de alimentos (Laticínios, Carnes, Frutas, etc.) | id, nome |
| **Alimento** | `alimento` | Tabela nutricional com calorias, proteínas, carboidratos, gorduras, fibras | id, nome, categoria, calorias, proteina, carboidrato, gordura, fibra |
| **Refeicao** | `refeicao` | Registro de refeição (CAFE_DA_MANHA, ALMOCO, JANTAR, LANCHE) | id, usuario, tipo, dataRefeicao, observacao |
| **AlimentoRefeicao** | `refeicao_alimento` | Itens consumidos em cada refeição | id, refeicao, alimento, quantidade, calorias |
| **MetaNutricional** | `meta_nutricional` | Metas nutricionais com periodicidade configurável | id, usuario, tipo, valorMeta, periodicidade, dataInicio, dataFim |
| **Objetivo** | `objetivo` | Objetivo principal do usuário (ganhar massa, reduzir gordura, manter peso) | id, usuario, tipo, dataInicio, dataFim, ativo |
| **RegistroDiario** | `registro_diario` | Acompanhamento diário: peso jejum, água (ml), passos | id, usuario, data, pesoJejum, aguaMl, passos |
| **AlimentoFavorito** | `alimento_favorito` | Alimentos marcados como favoritos | id, usuario, alimento |

---

[← Voltar ao README principal](../README.md)
