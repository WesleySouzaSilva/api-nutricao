package br.com.nutricao.services.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class Problema {

    private Integer status;
    private LocalDateTime horaDataErro;
    private String tipo;
    private String titulo;
    private String detalhe;
    private String mensagemUsuario;
    private List<Campo> campos;

    @Getter
    @Builder
    public static class Campo {
        private String nome;
        private String mensagem;
    }
}
