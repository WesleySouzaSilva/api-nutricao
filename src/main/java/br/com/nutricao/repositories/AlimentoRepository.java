package br.com.nutricao.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nutricao.domain.model.Alimento;

public interface AlimentoRepository extends JpaRepository<Alimento, Integer> {
    List<Alimento> findByCategoriaAlimentoId(Integer categoriaId);
    List<Alimento> findByNomeContaining(String nome);
}
