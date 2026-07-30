package br.com.nutricao.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nutricao.domain.model.CategoriaAlimento;

public interface CategoriaAlimentoRepository extends JpaRepository<CategoriaAlimento, Integer> {
    Optional<CategoriaAlimento> findByNome(String nome);
}
