package br.com.nutricao.domain.dto.visualizacao;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AlimentoRefeicaoResponse {

    private Integer id;
    private Integer refeicaoId;
    private Integer alimentoId;
    private BigDecimal quantidade;
    private String porcao;
}
