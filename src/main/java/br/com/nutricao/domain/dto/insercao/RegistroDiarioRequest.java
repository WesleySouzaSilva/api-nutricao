package br.com.nutricao.domain.dto.insercao;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroDiarioRequest {

    private Integer usuarioId;
    private LocalDate data;
    private BigDecimal caloriasConsumidas;
    private BigDecimal proteinasConsumidas;
    private BigDecimal carboidratosConsumidos;
    private BigDecimal gordurasConsumidas;
    private String observacoes;
}
