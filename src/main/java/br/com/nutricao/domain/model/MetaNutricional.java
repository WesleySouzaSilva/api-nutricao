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
@Table(name = "meta_nutricional")
public class MetaNutricional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private BigDecimal calorias;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private BigDecimal proteinas;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private BigDecimal carboidratos;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private BigDecimal gorduras;

    @Column(name = "data_inicio", nullable = false, columnDefinition = "DATE")
    private LocalDate dataInicio;

    @Column(name = "data_fim", columnDefinition = "DATE")
    private LocalDate dataFim;

    public MetaNutricional(Integer id) {
        this.id = id;
    }
}
