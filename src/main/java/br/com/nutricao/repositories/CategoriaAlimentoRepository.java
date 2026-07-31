package br.com.nutricao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nutricao.domain.CategoriaAlimento;

public interface CategoriaAlimentoRepository extends JpaRepository<CategoriaAlimento, Integer> {
    Optional<CategoriaAlimento> findByNome(String nome);
}
