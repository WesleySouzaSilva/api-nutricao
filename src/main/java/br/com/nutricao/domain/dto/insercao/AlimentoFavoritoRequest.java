package br.com.nutricao.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlimentoFavoritoRequest {

    private Integer usuarioId;
    private Integer alimentoId;
}
