package br.com.nutricao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nutricao.services.exception.entidades.EntidadeNaoEncontradaException;
import br.com.nutricao.domain.Objetivo;
import br.com.nutricao.domain.dto.filtro.ObjetivoCamposFiltro;
import br.com.nutricao.repositories.ObjetivoRepository;
import br.com.nutricao.specification.ObjetivoFiltro;

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

    public List<Objetivo> listar() {
        return objetivoRepository.findAll();
    }

    public Page<Objetivo> listarTodosFiltro(ObjetivoCamposFiltro filtro, Pageable pageable) {
        Specification<Objetivo> spec = ObjetivoFiltro.filtrar(filtro);
        return objetivoRepository.findAll(spec, pageable);
    }

    @Transactional
    public Objetivo atualizar(Integer id, Objetivo objetivo) {
        Objetivo existente = objetivoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Objetivo nao encontrado: " + id));
        if (objetivo.getTipo() != null) existente.setTipo(objetivo.getTipo());
        if (objetivo.getPesoAlvo() != null) existente.setPesoAlvo(objetivo.getPesoAlvo());
        if (objetivo.getCaloriasDiarias() != null) existente.setCaloriasDiarias(objetivo.getCaloriasDiarias());
        if (objetivo.getDataInicio() != null) existente.setDataInicio(objetivo.getDataInicio());
        if (objetivo.getDataFim() != null) existente.setDataFim(objetivo.getDataFim());
        if (objetivo.getDescricao() != null) existente.setDescricao(objetivo.getDescricao());
        if (objetivo.getUsuario() != null) existente.setUsuario(objetivo.getUsuario());
        return objetivoRepository.save(existente);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!objetivoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Objetivo nao encontrado: " + id);
        }
        objetivoRepository.deleteById(id);
    }
}
