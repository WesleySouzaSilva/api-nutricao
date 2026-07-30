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
    void buscarPorCategoria_DeveRetornar() {
        when(alimentoRepository.findByCategoriaAlimentoId(1)).thenReturn(Arrays.asList(alimento));

        List<Alimento> result = alimentoService.buscarPorCategoria(1);

        assertEquals(1, result.size());
    }

    @Test
    void buscarPorNome_DeveRetornar() {
        when(alimentoRepository.findByNomeContaining("Mac")).thenReturn(Arrays.asList(alimento));

        List<Alimento> result = alimentoService.buscarPorNome("Mac");

        assertEquals(1, result.size());
        assertEquals("Maca", result.get(0).getNome());
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
}
