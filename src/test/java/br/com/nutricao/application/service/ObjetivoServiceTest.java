package br.com.nutricao.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.nutricao.domain.model.Objetivo;
import br.com.nutricao.domain.model.Usuario;
import br.com.nutricao.infrastructure.persistence.ObjetivoRepository;

@ExtendWith(MockitoExtension.class)
class ObjetivoServiceTest {

    @Mock
    private ObjetivoRepository objetivoRepository;

    @InjectMocks
    private ObjetivoService objetivoService;

    private Objetivo objetivo;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        objetivo = new Objetivo();
        objetivo.setId(1);
        objetivo.setUsuario(usuario);
        objetivo.setTipo("EMAGRECER");
        objetivo.setDataInicio(LocalDate.now());
    }

    @Test
    void criar_DeveSalvar() {
        when(objetivoRepository.save(objetivo)).thenReturn(objetivo);

        Objetivo result = objetivoService.criar(objetivo);

        assertNotNull(result);
        assertEquals("EMAGRECER", result.getTipo());
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar() {
        when(objetivoRepository.findById(1)).thenReturn(Optional.of(objetivo));

        Optional<Objetivo> result = objetivoService.buscarPorId(1);

        assertTrue(result.isPresent());
    }

    @Test
    void buscarPorUsuario_DeveRetornar() {
        when(objetivoRepository.findByUsuarioId(1)).thenReturn(Arrays.asList(objetivo));

        List<Objetivo> result = objetivoService.buscarPorUsuario(1);

        assertEquals(1, result.size());
    }

    @Test
    void atualizar_QuandoExistir_DeveAtualizar() {
        Objetivo atualizado = new Objetivo();
        atualizado.setTipo("HIPERTROFIA");

        when(objetivoRepository.existsById(1)).thenReturn(true);
        when(objetivoRepository.save(any())).thenReturn(atualizado);

        Objetivo result = objetivoService.atualizar(1, atualizado);

        assertEquals("HIPERTROFIA", result.getTipo());
    }

    @Test
    void deletar_QuandoExistir_DeveRemover() {
        when(objetivoRepository.existsById(1)).thenReturn(true);

        objetivoService.deletar(1);

        verify(objetivoRepository).deleteById(1);
    }

    @Test
    void deletar_QuandoNaoExistir_DeveLancarExcecao() {
        when(objetivoRepository.existsById(99)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> objetivoService.deletar(99));
    }
}
