package br.com.nutricao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nutricao.domain.AlimentoFavorito;

public interface AlimentoFavoritoRepository extends JpaRepository<AlimentoFavorito, Integer> {
    List<AlimentoFavorito> findByUsuarioIdOrderByDataAdicaoDesc(Integer usuarioId);
    boolean existsByUsuarioIdAndAlimentoId(Integer usuarioId, Integer alimentoId);
    void deleteByUsuarioIdAndAlimentoId(Integer usuarioId, Integer alimentoId);
}
