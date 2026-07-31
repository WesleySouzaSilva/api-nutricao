package br.com.nutricao.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nutricao.api.exception.EntidadeNaoEncontradaException;
import br.com.nutricao.domain.model.AlimentoRefeicao;
import br.com.nutricao.infrastructure.persistence.AlimentoRefeicaoRepository;

@Service
public class AlimentoRefeicaoService {

    private final AlimentoRefeicaoRepository alimentoRefeicaoRepository;

    public AlimentoRefeicaoService(AlimentoRefeicaoRepository alimentoRefeicaoRepository) {
        this.alimentoRefeicaoRepository = alimentoRefeicaoRepository;
    }

    @Transactional
    public AlimentoRefeicao criar(AlimentoRefeicao alimentoRefeicao) {
        return alimentoRefeicaoRepository.save(alimentoRefeicao);
    }

    public Optional<AlimentoRefeicao> buscarPorId(Integer id) {
        return alimentoRefeicaoRepository.findById(id);
    }

    public List<AlimentoRefeicao> buscarPorRefeicao(Integer refeicaoId) {
        return alimentoRefeicaoRepository.findByRefeicaoId(refeicaoId);
    }

    @Transactional
    public void deletarPorRefeicao(Integer refeicaoId) {
        alimentoRefeicaoRepository.deleteByRefeicaoId(refeicaoId);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!alimentoRefeicaoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Vinculo AlimentoRefeicao nao encontrado: " + id);
        }
        alimentoRefeicaoRepository.deleteById(id);
    }
}
