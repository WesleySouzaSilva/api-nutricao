package br.com.nutricao.application.service;

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

import br.com.nutricao.api.exception.EntidadeNaoEncontradaException;
import br.com.nutricao.domain.model.Alimento;
import br.com.nutricao.domain.model.CategoriaAlimento;
import br.com.nutricao.infrastructure.persistence.AlimentoRepository;

@ExtendWith(MockitoExtension.class)
class AlimentoServiceTest {

    @Mock
    private AlimentoRepository alimentoRepository;

    @InjectMocks
    private AlimentoService alimentoService;

    private Alimento alimento;

    @BeforeEach
    void setUp() {
        CategoriaAlimento categoria = new CategoriaAlimento(1, "Frutas");
        alimento = new Alimento();
        alimento.setId(1);
        alimento.setNome("Maca");
        alimento.setCategoriaAlimento(categoria);
    }

    @Test
    void criar_DeveSalvar() {
        when(alimentoRepository.save(alimento)).thenReturn(alimento);

        Alimento result = alimentoService.criar(alimento);

        assertNotNull(result);
        assertEquals("Maca", result.getNome());
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar() {
        when(alimentoRepository.findById(1)).thenReturn(Optional.of(alimento));

        Optional<Alimento> result = alimentoService.buscarPorId(1);

        assertTrue(result.isPresent());
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornarVazio() {
        when(alimentoRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Alimento> result = alimentoService.buscarPorId(99);

        assertFalse(result.isPresent());
    }

    @Test
    void buscarPorCategoria_DeveRetornar() {
        when(alimentoRepository.findByCategoriaAlimentoId(1)).thenReturn(Arrays.asList(alimento));

        List<Alimento> result = alimentoService.buscarPorCategoria(1);

        assertEquals(1, result.size());
    }

    @Test
    void buscarPorCategoria_ComCategoriaSemAlimentos_DeveRetornarListaVazia() {
        when(alimentoRepository.findByCategoriaAlimentoId(99)).thenReturn(Arrays.asList());

        List<Alimento> result = alimentoService.buscarPorCategoria(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarPorNome_DeveRetornar() {
        when(alimentoRepository.findByNomeContaining("Mac")).thenReturn(Arrays.asList(alimento));

        List<Alimento> result = alimentoService.buscarPorNome("Mac");

        assertEquals(1, result.size());
        assertEquals("Maca", result.get(0).getNome());
    }

    @Test
    void buscarPorNome_QuandoNaoExistir_DeveRetornarListaVazia() {
        when(alimentoRepository.findByNomeContaining("XYZ")).thenReturn(Arrays.asList());

        List<Alimento> result = alimentoService.buscarPorNome("XYZ");

        assertTrue(result.isEmpty());
    }

    @Test
    void listar_DeveRetornarTodos() {
        when(alimentoRepository.findAll()).thenReturn(Arrays.asList(alimento));

        List<Alimento> result = alimentoService.listar();

        assertEquals(1, result.size());
    }

    @Test
    void atualizar_QuandoExistir_DeveAtualizar() {
        Alimento atualizado = new Alimento();
        atualizado.setNome("Maca Atualizada");

        when(alimentoRepository.existsById(1)).thenReturn(true);
        when(alimentoRepository.save(any())).thenReturn(atualizado);

        Alimento result = alimentoService.atualizar(1, atualizado);

        assertEquals("Maca Atualizada", result.getNome());
    }

    @Test
    void deletar_QuandoExistir_DeveRemover() {
        when(alimentoRepository.existsById(1)).thenReturn(true);

        alimentoService.deletar(1);

        verify(alimentoRepository).deleteById(1);
    }

    @Test
    void deletar_QuandoNaoExistir_DeveLancarExcecao() {
        when(alimentoRepository.existsById(99)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> alimentoService.deletar(99));
    }

    @Test
    void atualizar_QuandoNaoExistir_DeveLancarExcecao() {
        when(alimentoRepository.existsById(99)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> alimentoService.atualizar(99, new Alimento()));
    }
}
