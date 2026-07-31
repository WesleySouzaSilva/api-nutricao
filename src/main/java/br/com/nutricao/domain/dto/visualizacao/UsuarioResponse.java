package br.com.nutricao.domain.dto.visualizacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioResponse {

    private Integer id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private BigDecimal altura;
    private String sexo;
    private String medida;
    private String tipoLogin;
    private LocalDateTime dataCadastro;
}
