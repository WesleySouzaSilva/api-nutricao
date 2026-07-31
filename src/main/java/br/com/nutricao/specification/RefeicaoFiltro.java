package br.com.nutricao.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.nutricao.domain.Refeicao;
import br.com.nutricao.domain.dto.filtro.RefeicaoCamposFiltro;

public class RefeicaoFiltro {

    private RefeicaoFiltro() {
    }

    public static Specification<Refeicao> filtrar(RefeicaoCamposFiltro filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getNome() != null && !filtro.getNome().isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nome")),
                        "%" + filtro.getNome().toLowerCase() + "%"));
            }

            if (filtro.getUsuarioId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("usuario").get("id"),
                        filtro.getUsuarioId()));
            }

            if (filtro.getDataRefeicaoInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("dataRefeicao"),
                        filtro.getDataRefeicaoInicio()));
            }

            if (filtro.getDataRefeicaoFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("dataRefeicao"),
                        filtro.getDataRefeicaoFim()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
