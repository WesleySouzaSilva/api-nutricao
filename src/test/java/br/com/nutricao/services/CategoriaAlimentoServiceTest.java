package br.com.nutricao.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.nutricao.services.exception.entidades.EntidadeNaoEncontradaException;
import br.com.nutricao.services.exception.NegocioException;
import br.com.nutricao.domain.CategoriaAlimento;
import br.com.nutricao.repositories.CategoriaAlimentoRepository;

@ExtendWith(MockitoExtension.class)
class CategoriaAlimentoServiceTest {

    @Mock
    private CategoriaAlimentoRepository categoriaAlimentoRepository;

    @InjectMocks
    private CategoriaAlimentoService categoriaAlimentoService;

    private CategoriaAlimento categoria;

    @BeforeEach
    void setUp() {
        categoria = new CategoriaAlimento(1, "Frutas");
    }

    @Test
    void criar_ComNomeNovo_DeveSalvar() {
        when(categoriaAlimentoRepository.findByNome("Frutas")).thenReturn(Optional.empty());
        when(categoriaAlimentoRepository.save(categoria)).thenReturn(categoria);

        CategoriaAlimento result = categoriaAlimentoService.criar(categoria);

        assertNotNull(result);
        assertEquals("Frutas", result.getNome());
    }

    @Test
    void criar_ComNomeExistente_DeveLancarExcecao() {
        when(categoriaAlimentoRepository.findByNome("Frutas")).thenReturn(Optional.of(categoria));

        assertThrows(NegocioException.class, () -> categoriaAlimentoService.criar(categoria));
        verify(categoriaAlimentoRepository, never()).save(any());
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar() {
        when(categoriaAlimentoRepository.findById(1)).thenReturn(Optional.of(categoria));

        Optional<CategoriaAlimento> result = categoriaAlimentoService.buscarPorId(1);

        assertTrue(result.isPresent());
    }

    @Test
    void buscarPorNome_DeveRetornar() {
        when(categoriaAlimentoRepository.findByNome("Frutas")).thenReturn(Optional.of(categoria));

        Optional<CategoriaAlimento> result = categoriaAlimentoService.buscarPorNome("Frutas");

        assertTrue(result.isPresent());
    }

    @Test
    void listar_DeveRetornarTodas() {
        when(categoriaAlimentoRepository.findAll()).thenReturn(Arrays.asList(categoria));

        List<CategoriaAlimento> result = categoriaAlimentoService.listar();

        assertEquals(1, result.size());
    }

    @Test
    void atualizar_QuandoExistir_DeveAtualizar() {
        CategoriaAlimento atualizada = new CategoriaAlimento(1, "Legumes");

        when(categoriaAlimentoRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(categoriaAlimentoRepository.findByNome("Legumes")).thenReturn(Optional.empty());
        when(categoriaAlimentoRepository.save(any())).thenReturn(atualizada);

        CategoriaAlimento result = categoriaAlimentoService.atualizar(1, atualizada);

        assertEquals("Legumes", result.getNome());
    }

    @Test
    void deletar_QuandoExistir_DeveRemover() {
        when(categoriaAlimentoRepository.existsById(1)).thenReturn(true);

        categoriaAlimentoService.deletar(1);

        verify(categoriaAlimentoRepository).deleteById(1);
    }

    @Test
    void deletar_QuandoNaoExistir_DeveLancarExcecao() {
        when(categoriaAlimentoRepository.existsById(99)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> categoriaAlimentoService.deletar(99));
    }

    @Test
    void atualizar_QuandoNaoExistir_DeveLancarExcecao() {
        when(categoriaAlimentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> categoriaAlimentoService.atualizar(99, new CategoriaAlimento()));
    }

    @Test
    void atualizar_ComNomeJaExistente_DeveLancarExcecao() {
        CategoriaAlimento atualizada = new CategoriaAlimento(1, "Legumes");
        CategoriaAlimento outra = new CategoriaAlimento(2, "Legumes");

        when(categoriaAlimentoRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(categoriaAlimentoRepository.findByNome("Legumes")).thenReturn(Optional.of(outra));

        assertThrows(NegocioException.class,
                () -> categoriaAlimentoService.atualizar(1, atualizada));
    }
}
