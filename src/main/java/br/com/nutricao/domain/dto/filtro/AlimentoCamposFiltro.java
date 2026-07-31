package br.com.nutricao.domain.dto.filtro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlimentoCamposFiltro {
    private String nome;
    private Integer categoriaAlimentoId;
}
