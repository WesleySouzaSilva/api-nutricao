package br.com.nutricao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nutricao.services.exception.entidades.EntidadeNaoEncontradaException;
import br.com.nutricao.domain.MetaNutricional;
import br.com.nutricao.repositories.MetaNutricionalRepository;

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
        MetaNutricional existente = metaNutricionalRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("MetaNutricional nao encontrada: " + id));
        if (meta.getCalorias() != null) existente.setCalorias(meta.getCalorias());
        if (meta.getProteinas() != null) existente.setProteinas(meta.getProteinas());
        if (meta.getCarboidratos() != null) existente.setCarboidratos(meta.getCarboidratos());
        if (meta.getGorduras() != null) existente.setGorduras(meta.getGorduras());
        if (meta.getDataInicio() != null) existente.setDataInicio(meta.getDataInicio());
        if (meta.getDataFim() != null) existente.setDataFim(meta.getDataFim());
        if (meta.getUsuario() != null) existente.setUsuario(meta.getUsuario());
        return metaNutricionalRepository.save(existente);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!metaNutricionalRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("MetaNutricional nao encontrada: " + id);
        }
        metaNutricionalRepository.deleteById(id);
    }
}
