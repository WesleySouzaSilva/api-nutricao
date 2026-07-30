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

import br.com.nutricao.domain.model.Usuario;
import br.com.nutricao.infrastructure.persistence.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Joao");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("123");
        usuario.setSexo("MASCULINO");
        usuario.setMedida("KG");
        usuario.setTipoLogin("EMAIL");
    }

    @Test
    void criar_ComEmailNovo_DeveSalvar() {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario result = usuarioService.criar(usuario);

        assertNotNull(result);
        assertEquals("joao@email.com", result.getEmail());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void criar_ComEmailExistente_DeveLancarExcecao() {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        assertThrows(IllegalArgumentException.class, () -> usuarioService.criar(usuario));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.buscarPorId(1);

        assertTrue(result.isPresent());
        assertEquals("Joao", result.get().getNome());
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornarVazio() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Usuario> result = usuarioService.buscarPorId(99);

        assertFalse(result.isPresent());
    }

    @Test
    void buscarPorEmail_DeveRetornar() {
        when(usuarioRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.buscarPorEmail("joao@email.com");

        assertTrue(result.isPresent());
    }

    @Test
    void listar_DeveRetornarTodos() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario));

        List<Usuario> result = usuarioService.listar();

        assertEquals(1, result.size());
    }

    @Test
    void atualizar_QuandoExistir_DeveAtualizar() {
        Usuario atualizado = new Usuario();
        atualizado.setNome("Joao Atualizado");
        atualizado.setEmail("joao@email.com");
        atualizado.setSenha("123");
        atualizado.setSexo("MASCULINO");
        atualizado.setMedida("KG");
        atualizado.setTipoLogin("EMAIL");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(atualizado);

        Usuario result = usuarioService.atualizar(1, atualizado);

        assertEquals("Joao Atualizado", result.getNome());
    }

    @Test
    void atualizar_QuandoNaoExistir_DeveLancarExcecao() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> usuarioService.atualizar(99, new Usuario()));
    }

    @Test
    void deletar_QuandoExistir_DeveRemover() {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        usuarioService.deletar(1);

        verify(usuarioRepository).deleteById(1);
    }

    @Test
    void deletar_QuandoNaoExistir_DeveLancarExcecao() {
        when(usuarioRepository.existsById(99)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> usuarioService.deletar(99));
        verify(usuarioRepository, never()).deleteById(any());
    }
}
