package br.com.nutricao.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlimentoRequest {

    private String nome;
    private String kcal;
    private String proteina;
    private String gordura;
    private String carboidrato;
    private String fibraAlimentar;
    private String sodio;
    private Integer categoriaAlimentoId;
}
