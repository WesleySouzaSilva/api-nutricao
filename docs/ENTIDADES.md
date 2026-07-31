# Entidades do Dominio

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
         │               │ calorias         │     │ data             │     │ created_at       │
         │               │ proteinas        │     │ calorias_cons    │     └──────────────────┘
         │               │ carboidratos     │     │ proteinas_cons   │
         │               │ gorduras         │     │ carboidratos_cons│
         │               │ data_inicio      │     │ gorduras_cons    │
         │               │ data_fim         │     │ observacoes      │
         │               └──────────────────┘     └──────────────────┘
         │
         │1              ┌────────────────────────┐
         ├──────────────N│      Objetivo           │
         │               ├────────────────────────┤
         │               │ id (PK)                │
         │               │ usuario_id (FK)        │
         │               │ tipo (GANHAR_MASSA/     │
         │               │   REDUZIR_GORDURA/      │
         │               │   MANTER_PESO)          │
         │               │ peso_alvo               │
         │               │ calorias_diarias        │
         │               │ data_inicio             │
         │               │ data_fim                │
         │               │ descricao               │
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

## Descricao das Entidades

| Entidade | Tabela | Finalidade | Campos principais |
|---|---|---|---|
| **Usuario** | `usuario` | Cadastro e autenticacao de usuarios | id, nome, senha, email, dataNascimento, altura, sexo, dataCadastro, medida, tipoLogin, tokenId |
| **CategoriaAlimento** | `categoria_alimento` | Classificacao de alimentos (Laticinios, Carnes, Frutas, etc.) | id, nome |
| **Alimento** | `alimento` | Tabela nutricional com calorias, proteinas, carboidratos, gorduras, fibras | id, nome, categoria, calorias, proteina, carboidrato, gordura, fibra |
| **Refeicao** | `refeicao` | Registro de refeicao (CAFE_DA_MANHA, ALMOCO, JANTAR, LANCHE) | id, usuario, tipo, dataRefeicao, observacao |
| **AlimentoRefeicao** | `refeicao_alimento` | Itens consumidos em cada refeicao | id, refeicao, alimento, quantidade, calorias |
| **MetaNutricional** | `meta_nutricional` | Metas de macronutrientes (calorias, proteinas, carboidratos, gorduras) | id, usuario, calorias, proteinas, carboidratos, gorduras, dataInicio, dataFim |
| **Objetivo** | `objetivo` | Objetivo principal do usuario (ganhar massa, reduzir gordura, manter peso) | id, usuario, tipo, pesoAlvo, caloriasDiarias, dataInicio, dataFim, descricao |
| **RegistroDiario** | `registro_diario` | Acompanhamento diario de macronutrientes consumidos e observacoes | id, usuario, data, caloriasConsumidas, proteinasConsumidas, carboidratosConsumidos, gordurasConsumidas, observacoes |
| **AlimentoFavorito** | `alimento_favorito` | Alimentos marcados como favoritos | id, usuario, alimento |

---

[← Voltar ao README principal](../README.md)
