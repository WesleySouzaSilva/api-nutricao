package br.com.nutricao.domain.dto.insercao;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {

    private String nome;
    private String senha;
    private String email;
    private LocalDate dataNascimento;
    private BigDecimal altura;
    private String sexo;
    private String medida;
    private String tipoLogin;
}
