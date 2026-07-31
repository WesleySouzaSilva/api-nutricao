package br.com.nutricao.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.nutricao.domain.MetaNutricional;
import br.com.nutricao.domain.dto.filtro.MetaNutricionalCamposFiltro;

public class MetaNutricionalFiltro {

    private MetaNutricionalFiltro() {
    }

    public static Specification<MetaNutricional> filtrar(MetaNutricionalCamposFiltro filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getUsuarioId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("usuario").get("id"),
                        filtro.getUsuarioId()));
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
