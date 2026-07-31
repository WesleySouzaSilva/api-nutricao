package br.com.nutricao.domain.dto.visualizacao;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ObjetivoResponse {

    private Integer id;
    private Integer usuarioId;
    private String tipo;
    private BigDecimal pesoAlvo;
    private BigDecimal caloriasDiarias;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String descricao;
}
