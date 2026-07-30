package br.com.nutricao.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nutricao.domain.model.MetaNutricional;
import br.com.nutricao.infrastructure.persistence.MetaNutricionalRepository;

@Service
public class MetaNutricionalService {

    private final MetaNutricionalRepository metaNutricionalRepository;

    public MetaNutricionalService(MetaNutricionalRepository metaNutricionalRepository) {
        this.metaNutricionalRepository = metaNutricionalRepository;
    }

    @Transactional
    public MetaNutricional criar(MetaNutricional meta) {
        return metaNutricionalRepository.save(meta);
    }

    public Optional<MetaNutricional> buscarPorId(Integer id) {
        return metaNutricionalRepository.findById(id);
    }

    public List<MetaNutricional> buscarPorUsuario(Integer usuarioId) {
        return metaNutricionalRepository.findByUsuarioId(usuarioId);
    }

    public Optional<MetaNutricional> buscarUltimaPorUsuario(Integer usuarioId) {
        return metaNutricionalRepository.findFirstByUsuarioIdOrderByDataInicioDesc(usuarioId);
    }

    public List<MetaNutricional> listar() {
        return metaNutricionalRepository.findAll();
    }

    @Transactional
    public MetaNutricional atualizar(Integer id, MetaNutricional meta) {
        if (!metaNutricionalRepository.existsById(id)) {
            throw new IllegalArgumentException("MetaNutricional nao encontrada: " + id);
        }
        meta.setId(id);
        return metaNutricionalRepository.save(meta);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!metaNutricionalRepository.existsById(id)) {
            throw new IllegalArgumentException("MetaNutricional nao encontrada: " + id);
        }
        metaNutricionalRepository.deleteById(id);
    }
}
