package br.com.nutricao.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MetaNutricionalResponse {

    private Integer id;
    private Integer usuarioId;
    private BigDecimal calorias;
    private BigDecimal proteinas;
    private BigDecimal carboidratos;
    private BigDecimal gorduras;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
