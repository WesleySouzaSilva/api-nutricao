package br.com.nutricao.domain.dto.insercao;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjetivoRequest {

    private Integer usuarioId;
    private String tipo;
    private BigDecimal pesoAlvo;
    private BigDecimal caloriasDiarias;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String descricao;
}
