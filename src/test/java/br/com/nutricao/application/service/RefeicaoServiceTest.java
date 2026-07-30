package br.com.nutricao.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.nutricao.domain.model.Refeicao;
import br.com.nutricao.domain.model.Usuario;
import br.com.nutricao.infrastructure.persistence.RefeicaoRepository;

@ExtendWith(MockitoExtension.class)
class RefeicaoServiceTest {

    @Mock
    private RefeicaoRepository refeicaoRepository;

    @InjectMocks
    private RefeicaoService refeicaoService;

    private Refeicao refeicao;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        refeicao = new Refeicao();
        refeicao.setId(1);
        refeicao.setNome("Almoco");
        refeicao.setDataRefeicao(LocalDateTime.now());
        refeicao.setUsuario(usuario);
    }

    @Test
    void criar_DeveSalvar() {
        when(refeicaoRepository.save(refeicao)).thenReturn(refeicao);

        Refeicao result = refeicaoService.criar(refeicao);

        assertNotNull(result);
        assertEquals("Almoco", result.getNome());
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar() {
        when(refeicaoRepository.findById(1)).thenReturn(Optional.of(refeicao));

        Optional<Refeicao> result = refeicaoService.buscarPorId(1);

        assertTrue(result.isPresent());
    }

    @Test
    void buscarPorUsuario_DeveRetornar() {
        when(refeicaoRepository.findByUsuarioIdOrderByDataRefeicaoDesc(1)).thenReturn(Arrays.asList(refeicao));

        List<Refeicao> result = refeicaoService.buscarPorUsuario(1);

        assertEquals(1, result.size());
    }

    @Test
    void listar_DeveRetornarTodas() {
        when(refeicaoRepository.findAll()).thenReturn(Arrays.asList(refeicao));

        List<Refeicao> result = refeicaoService.listar();

        assertEquals(1, result.size());
    }

    @Test
    void atualizar_QuandoNaoExistir_DeveLancarExcecao() {
        when(refeicaoRepository.existsById(99)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> refeicaoService.atualizar(99, new Refeicao()));
    }

    @Test
    void deletar_QuandoExistir_DeveRemover() {
        when(refeicaoRepository.existsById(1)).thenReturn(true);

        refeicaoService.deletar(1);

        verify(refeicaoRepository).deleteById(1);
    }
}
