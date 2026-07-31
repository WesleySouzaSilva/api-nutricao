package br.com.nutricao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.nutricao.domain.Objetivo;

public interface ObjetivoRepository extends JpaRepository<Objetivo, Integer>, JpaSpecificationExecutor<Objetivo> {
    List<Objetivo> findByUsuarioId(Integer usuarioId);
}
