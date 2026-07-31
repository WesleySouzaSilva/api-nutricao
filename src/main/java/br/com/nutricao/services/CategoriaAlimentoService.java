package br.com.nutricao.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nutricao.api.exception.EntidadeNaoEncontradaException;
import br.com.nutricao.api.exception.NegocioException;
import br.com.nutricao.domain.model.CategoriaAlimento;
import br.com.nutricao.infrastructure.persistence.CategoriaAlimentoRepository;

@Service
public class CategoriaAlimentoService {

    private final CategoriaAlimentoRepository categoriaAlimentoRepository;

    public CategoriaAlimentoService(CategoriaAlimentoRepository categoriaAlimentoRepository) {
        this.categoriaAlimentoRepository = categoriaAlimentoRepository;
    }

    @Transactional
    public CategoriaAlimento criar(CategoriaAlimento categoria) {
        if (categoriaAlimentoRepository.findByNome(categoria.getNome()).isPresent()) {
            throw new NegocioException("Categoria ja existente: " + categoria.getNome());
        }
        return categoriaAlimentoRepository.save(categoria);
    }

    public Optional<CategoriaAlimento> buscarPorId(Integer id) {
        return categoriaAlimentoRepository.findById(id);
    }

    public Optional<CategoriaAlimento> buscarPorNome(String nome) {
        return categoriaAlimentoRepository.findByNome(nome);
    }

    public List<CategoriaAlimento> listar() {
        return categoriaAlimentoRepository.findAll();
    }

    @Transactional
    public CategoriaAlimento atualizar(Integer id, CategoriaAlimento categoria) {
        CategoriaAlimento existente = categoriaAlimentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Categoria nao encontrada: " + id));

        if (!existente.getNome().equals(categoria.getNome())
                && categoriaAlimentoRepository.findByNome(categoria.getNome()).isPresent()) {
            throw new NegocioException("Categoria ja existente: " + categoria.getNome());
        }

        categoria.setId(id);
        return categoriaAlimentoRepository.save(categoria);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!categoriaAlimentoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Categoria nao encontrada: " + id);
        }
        categoriaAlimentoRepository.deleteById(id);
    }
}
