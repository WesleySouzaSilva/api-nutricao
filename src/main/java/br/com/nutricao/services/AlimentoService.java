package br.com.nutricao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nutricao.services.exception.entidades.EntidadeNaoEncontradaException;
import br.com.nutricao.domain.Alimento;
import br.com.nutricao.repositories.AlimentoRepository;

@Service
public class AlimentoService {

    private final AlimentoRepository alimentoRepository;

    public AlimentoService(AlimentoRepository alimentoRepository) {
        this.alimentoRepository = alimentoRepository;
    }

    @Transactional
    public Alimento criar(Alimento alimento) {
        return alimentoRepository.save(alimento);
    }

    public Optional<Alimento> buscarPorId(Integer id) {
        return alimentoRepository.findById(id);
    }

    public List<Alimento> buscarPorCategoria(Integer categoriaId) {
        return alimentoRepository.findByCategoriaAlimentoId(categoriaId);
    }

    public List<Alimento> buscarPorNome(String nome) {
        return alimentoRepository.findByNomeContaining(nome);
    }

    public List<Alimento> listar() {
        return alimentoRepository.findAll();
    }

    @Transactional
    public Alimento atualizar(Integer id, Alimento alimento) {
        if (!alimentoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Alimento nao encontrado: " + id);
        }
        alimento.setId(id);
        return alimentoRepository.save(alimento);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!alimentoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Alimento nao encontrado: " + id);
        }
        alimentoRepository.deleteById(id);
    }
}
