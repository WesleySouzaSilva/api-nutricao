package br.com.nutricao.domain.dto.visualizacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AlimentoResponse {

    private Integer id;
    private String nome;
    private String kcal;
    private String proteina;
    private String gordura;
    private String carboidrato;
    private String fibraAlimentar;
    private String sodio;
    private CategoriaAlimentoResponse categoria;
}
