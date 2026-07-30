package br.com.nutricao.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nutricao.domain.model.Objetivo;

public interface ObjetivoRepository extends JpaRepository<Objetivo, Integer> {
    List<Objetivo> findByUsuarioId(Integer usuarioId);
}
