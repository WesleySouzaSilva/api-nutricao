package br.com.nutricao.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nutricao.services.exception.entidades.EntidadeNaoEncontradaException;
import br.com.nutricao.domain.Refeicao;
import br.com.nutricao.domain.dto.filtro.RefeicaoCamposFiltro;
import br.com.nutricao.repositories.RefeicaoRepository;
import br.com.nutricao.specification.RefeicaoFiltro;

@Service
public class RefeicaoService {

    private final RefeicaoRepository refeicaoRepository;

    public RefeicaoService(RefeicaoRepository refeicaoRepository) {
        this.refeicaoRepository = refeicaoRepository;
    }

    @Transactional
    public Refeicao criar(Refeicao refeicao) {
        return refeicaoRepository.save(refeicao);
    }

    public Optional<Refeicao> buscarPorId(Integer id) {
        return refeicaoRepository.findById(id);
    }

    public List<Refeicao> buscarPorUsuario(Integer usuarioId) {
        return refeicaoRepository.findByUsuarioIdOrderByDataRefeicaoDesc(usuarioId);
    }

    public List<Refeicao> buscarPorUsuarioEPeriodo(Integer usuarioId, LocalDateTime inicio, LocalDateTime fim) {
        return refeicaoRepository.findByUsuarioIdAndDataRefeicaoBetweenOrderByDataRefeicaoDesc(usuarioId, inicio, fim);
    }

    public List<Refeicao> listar() {
        return refeicaoRepository.findAll();
    }

    public Page<Refeicao> listarTodosFiltro(RefeicaoCamposFiltro filtro, Pageable pageable) {
        Specification<Refeicao> spec = RefeicaoFiltro.filtrar(filtro);
        return refeicaoRepository.findAll(spec, pageable);
    }

    @Transactional
    public Refeicao atualizar(Integer id, Refeicao refeicao) {
        Refeicao existente = refeicaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Refeicao nao encontrada: " + id));
        if (refeicao.getNome() != null) existente.setNome(refeicao.getNome());
        if (refeicao.getDataRefeicao() != null) existente.setDataRefeicao(refeicao.getDataRefeicao());
        if (refeicao.getUsuario() != null) existente.setUsuario(refeicao.getUsuario());
        return refeicaoRepository.save(existente);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!refeicaoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Refeicao nao encontrada: " + id);
        }
        refeicaoRepository.deleteById(id);
    }
}
