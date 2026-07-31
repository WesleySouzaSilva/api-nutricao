package br.com.nutricao.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, length = 200)
    private String senha;

    @Column(nullable = false, length = 200, unique = true)
    private String email;

    @Column(name = "data_nascimento", columnDefinition = "DATE")
    private LocalDate dataNascimento;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private BigDecimal altura;

    @Column(nullable = false, length = 50)
    private String sexo;

    @Column(name = "data_cadastro", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime dataCadastro;

    @Column(nullable = false, length = 10)
    private String medida;

    @Column(name = "tipo_login", nullable = false, length = 50)
    private String tipoLogin;

    @Column(name = "token_id", length = 2000)
    private String tokenId;

    public Usuario(Integer id) {
        this.id = id;
    }
}
