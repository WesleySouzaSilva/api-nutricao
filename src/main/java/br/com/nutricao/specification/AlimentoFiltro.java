package br.com.nutricao.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.nutricao.domain.Alimento;
import br.com.nutricao.domain.dto.filtro.AlimentoCamposFiltro;

public class AlimentoFiltro {

    private AlimentoFiltro() {
    }

    public static Specification<Alimento> filtrar(AlimentoCamposFiltro filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getNome() != null && !filtro.getNome().isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nome")),
                        "%" + filtro.getNome().toLowerCase() + "%"));
            }

            if (filtro.getCategoriaAlimentoId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("categoriaAlimento").get("id"),
                        filtro.getCategoriaAlimentoId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
