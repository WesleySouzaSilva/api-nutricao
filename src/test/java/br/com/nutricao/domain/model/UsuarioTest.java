package br.com.nutricao.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void criarUsuario_ComDadosValidos_DeveInstanciarCorretamente() {
        Usuario usuario = new Usuario();
        usuario.setNome("Joao Silva");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("senha123");
        usuario.setDataNascimento(LocalDate.of(1990, 1, 15));
        usuario.setAltura(new BigDecimal("1.75"));
        usuario.setSexo("MASCULINO");
        usuario.setMedida("KG");
        usuario.setTipoLogin("EMAIL");
        usuario.setDataCadastro(LocalDateTime.now());

        assertNotNull(usuario);
        assertEquals("Joao Silva", usuario.getNome());
        assertEquals("joao@email.com", usuario.getEmail());
        assertEquals("senha123", usuario.getSenha());
        assertEquals(LocalDate.of(1990, 1, 15), usuario.getDataNascimento());
        assertEquals(0, new BigDecimal("1.75").compareTo(usuario.getAltura()));
        assertEquals("MASCULINO", usuario.getSexo());
        assertEquals("KG", usuario.getMedida());
        assertEquals("EMAIL", usuario.getTipoLogin());
        assertNotNull(usuario.getDataCadastro());
    }

    @Test
    void equals_ComMesmoId_DeveSerTrue() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1);
        Usuario usuario2 = new Usuario();
        usuario2.setId(1);

        assertEquals(usuario1, usuario2);
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }

    @Test
    void equals_ComIdsDiferentes_DeveSerFalse() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1);
        Usuario usuario2 = new Usuario();
        usuario2.setId(2);

        assertNotEquals(usuario1, usuario2);
    }

    @Test
    void equals_ComIdNull_DeveSerFalse() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(null);
        Usuario usuario2 = new Usuario();
        usuario2.setId(null);

        assertEquals(usuario1, usuario2);
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }
}
