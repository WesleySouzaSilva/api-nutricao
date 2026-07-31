package br.com.nutricao.domain.dto.insercao;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlimentoRefeicaoRequest {

    private Integer refeicaoId;
    private Integer alimentoId;
    private BigDecimal quantidade;
    private String porcao;
}
