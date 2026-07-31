package br.com.nutricao.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoriaAlimentoResponse {

    private Integer id;
    private String nome;
}
