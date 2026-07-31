package br.com.nutricao.domain.dto.visualizacao;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistroDiarioResponse {

    private Integer id;
    private Integer usuarioId;
    private LocalDate data;
    private BigDecimal caloriasConsumidas;
    private BigDecimal proteinasConsumidas;
    private BigDecimal carboidratosConsumidos;
    private BigDecimal gordurasConsumidas;
    private String observacoes;
}
