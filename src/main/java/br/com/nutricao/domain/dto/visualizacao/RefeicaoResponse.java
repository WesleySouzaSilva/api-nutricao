package br.com.nutricao.application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RefeicaoResponse {

    private Integer id;
    private String nome;
    private LocalDateTime dataRefeicao;
    private Integer usuarioId;
}
