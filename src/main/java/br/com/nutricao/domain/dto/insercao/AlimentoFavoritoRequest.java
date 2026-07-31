package br.com.nutricao.domain.dto.insercao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlimentoFavoritoRequest {

    private Integer usuarioId;
    private Integer alimentoId;
}
