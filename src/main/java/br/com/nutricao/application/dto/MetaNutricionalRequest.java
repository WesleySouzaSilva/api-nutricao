package br.com.nutricao.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetaNutricionalRequest {

    private Integer usuarioId;
    private BigDecimal calorias;
    private BigDecimal proteinas;
    private BigDecimal carboidratos;
    private BigDecimal gorduras;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
