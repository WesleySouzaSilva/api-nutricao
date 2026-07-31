package br.com.nutricao.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nutricao.domain.Refeicao;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Integer> {
    List<Refeicao> findByUsuarioIdOrderByDataRefeicaoDesc(Integer usuarioId);
    List<Refeicao> findByUsuarioIdAndDataRefeicaoBetweenOrderByDataRefeicaoDesc(Integer usuarioId, LocalDateTime inicio, LocalDateTime fim);
}
