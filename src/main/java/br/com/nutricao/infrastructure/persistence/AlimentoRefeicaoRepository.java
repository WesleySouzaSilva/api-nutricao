package br.com.nutricao.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nutricao.domain.model.AlimentoRefeicao;

public interface AlimentoRefeicaoRepository extends JpaRepository<AlimentoRefeicao, Integer> {
    List<AlimentoRefeicao> findByRefeicaoId(Integer refeicaoId);
    void deleteByRefeicaoId(Integer refeicaoId);
}
