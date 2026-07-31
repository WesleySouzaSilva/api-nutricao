package br.com.nutricao.application.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefeicaoRequest {

    private String nome;
    private LocalDateTime dataRefeicao;
    private Integer usuarioId;
}
