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
import br.com.nutricao.domain.model.AlimentoRefeicao;
import br.com.nutricao.domain.model.Refeicao;
import br.com.nutricao.infrastructure.persistence.AlimentoRefeicaoRepository;

@ExtendWith(MockitoExtension.class)
class AlimentoRefeicaoServiceTest {

    @Mock
    private AlimentoRefeicaoRepository alimentoRefeicaoRepository;

    @InjectMocks
    private AlimentoRefeicaoService alimentoRefeicaoService;

    private AlimentoRefeicao alimentoRefeicao;

    @BeforeEach
    void setUp() {
        Refeicao refeicao = new Refeicao();
        refeicao.setId(1);

        Alimento alimento = new Alimento();
        alimento.setId(1);
        alimento.setNome("Arroz");

        alimentoRefeicao = new AlimentoRefeicao();
        alimentoRefeicao.setId(1);
        alimentoRefeicao.setRefeicao(refeicao);
        alimentoRefeicao.setAlimento(alimento);
    }

    @Test
    void criar_DeveSalvar() {
        when(alimentoRefeicaoRepository.save(alimentoRefeicao)).thenReturn(alimentoRefeicao);

        AlimentoRefeicao result = alimentoRefeicaoService.criar(alimentoRefeicao);

        assertNotNull(result);
    }

    @Test
    void buscarPorRefeicao_DeveRetornar() {
        when(alimentoRefeicaoRepository.findByRefeicaoId(1)).thenReturn(Arrays.asList(alimentoRefeicao));

        List<AlimentoRefeicao> result = alimentoRefeicaoService.buscarPorRefeicao(1);

        assertEquals(1, result.size());
        assertEquals("Arroz", result.get(0).getAlimento().getNome());
    }

    @Test
    void deletarPorRefeicao_DeveRemoverVinculos() {
        alimentoRefeicaoService.deletarPorRefeicao(1);

        verify(alimentoRefeicaoRepository).deleteByRefeicaoId(1);
    }

    @Test
    void deletar_QuandoExistir_DeveRemover() {
        when(alimentoRefeicaoRepository.existsById(1)).thenReturn(true);

        alimentoRefeicaoService.deletar(1);

        verify(alimentoRefeicaoRepository).deleteById(1);
    }

    @Test
    void deletar_QuandoNaoExistir_DeveLancarExcecao() {
        when(alimentoRefeicaoRepository.existsById(99)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class, () -> alimentoRefeicaoService.deletar(99));
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar() {
        when(alimentoRefeicaoRepository.findById(1)).thenReturn(Optional.of(alimentoRefeicao));

        Optional<AlimentoRefeicao> result = alimentoRefeicaoService.buscarPorId(1);

        assertTrue(result.isPresent());
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornarVazio() {
        when(alimentoRefeicaoRepository.findById(99)).thenReturn(Optional.empty());

        Optional<AlimentoRefeicao> result = alimentoRefeicaoService.buscarPorId(99);

        assertFalse(result.isPresent());
    }
}
