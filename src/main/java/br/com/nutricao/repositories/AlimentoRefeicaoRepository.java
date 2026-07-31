package br.com.nutricao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.nutricao.domain.AlimentoRefeicao;

public interface AlimentoRefeicaoRepository extends JpaRepository<AlimentoRefeicao, Integer>, JpaSpecificationExecutor<AlimentoRefeicao> {
    List<AlimentoRefeicao> findByRefeicaoId(Integer refeicaoId);
    void deleteByRefeicaoId(Integer refeicaoId);
}
