package br.com.nutricao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.nutricao.domain.Alimento;

public interface AlimentoRepository extends JpaRepository<Alimento, Integer>, JpaSpecificationExecutor<Alimento> {
    List<Alimento> findByCategoriaAlimentoId(Integer categoriaId);
    List<Alimento> findByNomeContaining(String nome);
}
