package br.com.nutricao.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
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

import br.com.nutricao.api.exception.EntidadeNaoEncontradaException;
import br.com.nutricao.domain.model.RegistroDiario;
import br.com.nutricao.domain.model.Usuario;
import br.com.nutricao.infrastructure.persistence.RegistroDiarioRepository;

@ExtendWith(MockitoExtension.class)
class RegistroDiarioServiceTest {

    @Mock
    private RegistroDiarioRepository registroDiarioRepository;

    @InjectMocks
    private RegistroDiarioService registroDiarioService;

    private RegistroDiario registro;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        registro = new RegistroDiario();
        registro.setId(1);
        registro.setUsuario(usuario);
        registro.setData(LocalDate.now());
        registro.setCaloriasConsumidas(new BigDecimal("2000"));
    }

    @Test
    void criar_DeveSalvar() {
        when(registroDiarioRepository.save(registro)).thenReturn(registro);

        RegistroDiario result = registroDiarioService.criar(registro);

        assertNotNull(result);
        assertEquals(0, new BigDecimal("2000").compareTo(result.getCaloriasConsumidas()));
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar() {
        when(registroDiarioRepository.findById(1)).thenReturn(Optional.of(registro));

        Optional<RegistroDiario> result = registroDiarioService.buscarPorId(1);

        assertTrue(result.isPresent());
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornarVazio() {
        when(registroDiarioRepository.findById(99)).thenReturn(Optional.empty());

        Optional<RegistroDiario> result = registroDiarioService.buscarPorId(99);

        assertFalse(result.isPresent());
    }

    @Test
    void buscarPorUsuarioEData_DeveRetornar() {
        LocalDate hoje = LocalDate.now();
        when(registroDiarioRepository.findByUsuarioIdAndData(1, hoje)).thenReturn(Optional.of(registro));

        Optional<RegistroDiario> result = registroDiarioService.buscarPorUsuarioEData(1, hoje);

        assertTrue(result.isPresent());
    }

    @Test
    void buscarPorUsuarioEData_QuandoNaoExistir_DeveRetornarVazio() {
        LocalDate hoje = LocalDate.now();
        when(registroDiarioRepository.findByUsuarioIdAndData(99, hoje)).thenReturn(Optional.empty());

        Optional<RegistroDiario> result = registroDiarioService.buscarPorUsuarioEData(99, hoje);

        assertFalse(result.isPresent());
    }

    @Test
    void buscarPorUsuarioOrdenado_DeveRetornar() {
        when(registroDiarioRepository.findByUsuarioIdOrderByDataDesc(1)).thenReturn(Arrays.asList(registro));

        List<RegistroDiario> result = registroDiarioService.buscarPorUsuarioOrdenado(1);

        assertEquals(1, result.size());
    }

    @Test
    void atualizar_QuandoNaoExistir_DeveLancarExcecao() {
        when(registroDiarioRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> registroDiarioService.atualizar(99, new RegistroDiario()));
    }

    @Test
    void atualizar_QuandoExistir_DeveAtualizar() {
        RegistroDiario atualizado = new RegistroDiario();
        atualizado.setCaloriasConsumidas(new BigDecimal("2500"));

        when(registroDiarioRepository.findById(1)).thenReturn(Optional.of(registro));
        when(registroDiarioRepository.save(any())).thenReturn(atualizado);

        RegistroDiario result = registroDiarioService.atualizar(1, atualizado);

        assertEquals(0, new BigDecimal("2500").compareTo(result.getCaloriasConsumidas()));
    }

    @Test
    void atualizar_QuandoExistir_ComTodosCampos_DeveAtualizar() {
        RegistroDiario atualizado = new RegistroDiario();
        atualizado.setProteinasConsumidas(new BigDecimal("150"));
        atualizado.setCarboidratosConsumidos(new BigDecimal("300"));
        atualizado.setGordurasConsumidas(new BigDecimal("50"));
        atualizado.setObservacoes("Refeicao balanceada");
        atualizado.setUsuario(new Usuario(1));
        atualizado.setData(LocalDate.now());

        when(registroDiarioRepository.findById(1)).thenReturn(Optional.of(registro));
        when(registroDiarioRepository.save(any())).thenReturn(atualizado);

        RegistroDiario result = registroDiarioService.atualizar(1, atualizado);

        assertEquals(0, new BigDecimal("150").compareTo(result.getProteinasConsumidas()));
    }

    @Test
    void deletar_QuandoExistir_DeveRemover() {
        when(registroDiarioRepository.existsById(1)).thenReturn(true);

        registroDiarioService.deletar(1);

        verify(registroDiarioRepository).deleteById(1);
    }

    @Test
    void deletar_QuandoNaoExistir_DeveLancarExcecao() {
        when(registroDiarioRepository.existsById(99)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> registroDiarioService.deletar(99));
    }
}
