package br.com.nutricao.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "registro_diario")
public class RegistroDiario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate data;

    @Column(name = "calorias_consumidas", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal caloriasConsumidas;

    @Column(name = "proteinas_consumidas", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal proteinasConsumidas;

    @Column(name = "carboidratos_consumidos", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal carboidratosConsumidos;

    @Column(name = "gorduras_consumidas", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal gordurasConsumidas;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public RegistroDiario(Integer id) {
        this.id = id;
    }
}
