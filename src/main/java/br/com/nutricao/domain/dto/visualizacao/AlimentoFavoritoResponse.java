package br.com.nutricao.domain.dto.visualizacao;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AlimentoFavoritoResponse {

    private Integer id;
    private Integer usuarioId;
    private Integer alimentoId;
    private LocalDateTime dataAdicao;
}
