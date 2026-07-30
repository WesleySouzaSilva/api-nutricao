package br.com.nutricao.domain.model;

import java.io.Serializable;

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
@Table(name = "alimento")
public class Alimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(length = 10)
    private String kcal;

    @Column(length = 10)
    private String proteina;

    @Column(length = 10)
    private String gordura;

    @Column(length = 10)
    private String carboidrato;

    @Column(name = "fibra_alimentar", length = 10)
    private String fibraAlimentar;

    @Column(length = 10)
    private String sodio;

    @ManyToOne
    @JoinColumn(name = "categoria_alimento_id", nullable = false)
    private CategoriaAlimento categoriaAlimento;

    public Alimento(Integer id) {
        this.id = id;
    }
}
