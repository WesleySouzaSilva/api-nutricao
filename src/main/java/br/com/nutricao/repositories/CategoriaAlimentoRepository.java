package br.com.nutricao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.nutricao.domain.CategoriaAlimento;

public interface CategoriaAlimentoRepository extends JpaRepository<CategoriaAlimento, Integer>, JpaSpecificationExecutor<CategoriaAlimento> {
    Optional<CategoriaAlimento> findByNome(String nome);
}
