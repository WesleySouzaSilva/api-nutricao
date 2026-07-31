package br.com.nutricao.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nutricao.domain.model.AlimentoFavorito;
import br.com.nutricao.infrastructure.persistence.AlimentoFavoritoRepository;

@Service
public class AlimentoFavoritoService {

    private final AlimentoFavoritoRepository alimentoFavoritoRepository;

    public AlimentoFavoritoService(AlimentoFavoritoRepository alimentoFavoritoRepository) {
        this.alimentoFavoritoRepository = alimentoFavoritoRepository;
    }

    @Transactional
    public AlimentoFavorito adicionar(AlimentoFavorito favorito) {
        return alimentoFavoritoRepository.save(favorito);
    }

    public List<AlimentoFavorito> buscarPorUsuario(Integer usuarioId) {
        return alimentoFavoritoRepository.findByUsuarioIdOrderByDataAdicaoDesc(usuarioId);
    }

    public boolean existePorUsuarioEAlimento(Integer usuarioId, Integer alimentoId) {
        return alimentoFavoritoRepository.existsByUsuarioIdAndAlimentoId(usuarioId, alimentoId);
    }

    @Transactional
    public void remover(Integer usuarioId, Integer alimentoId) {
        alimentoFavoritoRepository.deleteByUsuarioIdAndAlimentoId(usuarioId, alimentoId);
    }
}
