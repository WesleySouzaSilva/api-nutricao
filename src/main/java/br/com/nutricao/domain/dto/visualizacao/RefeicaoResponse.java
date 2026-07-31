package br.com.nutricao.domain.dto.visualizacao;

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
