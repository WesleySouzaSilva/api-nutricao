package br.com.nutricao.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import br.com.nutricao.domain.model.Usuario;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Joao Silva");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("senha123");
        usuario.setSexo("MASCULINO");
        usuario.setMedida("KG");
        usuario.setTipoLogin("EMAIL");
        usuario.setDataCadastro(LocalDateTime.now());
        return usuario;
    }

    @Test
    void save_ComDadosValidos_DevePersistir() {
        Usuario usuario = criarUsuario();
        Usuario saved = usuarioRepository.save(usuario);
        assertNotNull(saved.getId());
        assertEquals("joao@email.com", saved.getEmail());
    }

    @Test
    void findByEmail_QuandoExistir_DeveRetornar() {
        Usuario usuario = criarUsuario();
        usuarioRepository.save(usuario);
        Optional<Usuario> found = usuarioRepository.findByEmail("joao@email.com");
        assertTrue(found.isPresent());
        assertEquals("Joao Silva", found.get().getNome());
    }

    @Test
    void findByEmail_QuandoNaoExistir_DeveRetornarEmpty() {
        Optional<Usuario> found = usuarioRepository.findByEmail("naoexiste@email.com");
        assertFalse(found.isPresent());
    }

    @Test
    void delete_ComIdExistente_DeveRemover() {
        Usuario usuario = usuarioRepository.save(criarUsuario());
        usuarioRepository.deleteById(usuario.getId());
        assertFalse(usuarioRepository.findById(usuario.getId()).isPresent());
    }

    @Test
    void save_ComEmailDuplicado_DeveLancarExcecao() {
        Usuario usuario = criarUsuario();
        usuarioRepository.saveAndFlush(usuario);

        Usuario duplicado = criarUsuario();
        assertThrows(DataIntegrityViolationException.class,
                () -> usuarioRepository.saveAndFlush(duplicado));
    }
}
