package br.com.nutricao.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nutricao.domain.model.Objetivo;
import br.com.nutricao.infrastructure.persistence.ObjetivoRepository;

@Service
public class ObjetivoService {

    private final ObjetivoRepository objetivoRepository;

    public ObjetivoService(ObjetivoRepository objetivoRepository) {
        this.objetivoRepository = objetivoRepository;
    }

    @Transactional
    public Objetivo criar(Objetivo objetivo) {
        return objetivoRepository.save(objetivo);
    }

    public Optional<Objetivo> buscarPorId(Integer id) {
        return objetivoRepository.findById(id);
    }

    public List<Objetivo> buscarPorUsuario(Integer usuarioId) {
        return objetivoRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Objetivo atualizar(Integer id, Objetivo objetivo) {
        if (!objetivoRepository.existsById(id)) {
            throw new IllegalArgumentException("Objetivo nao encontrado: " + id);
        }
        objetivo.setId(id);
        return objetivoRepository.save(objetivo);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!objetivoRepository.existsById(id)) {
            throw new IllegalArgumentException("Objetivo nao encontrado: " + id);
        }
        objetivoRepository.deleteById(id);
    }
}
