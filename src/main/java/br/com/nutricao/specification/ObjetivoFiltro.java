package br.com.nutricao.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.nutricao.domain.Objetivo;
import br.com.nutricao.domain.dto.filtro.ObjetivoCamposFiltro;

public class ObjetivoFiltro {

    private ObjetivoFiltro() {
    }

    public static Specification<Objetivo> filtrar(ObjetivoCamposFiltro filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getUsuarioId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("usuario").get("id"),
                        filtro.getUsuarioId()));
            }

            if (filtro.getTipo() != null && !filtro.getTipo().isBlank()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("tipo")),
                        filtro.getTipo().toLowerCase()));
            }

            if (filtro.getDataInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("dataInicio"),
                        filtro.getDataInicio()));
            }

            if (filtro.getDataFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("dataFim"),
                        filtro.getDataFim()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
