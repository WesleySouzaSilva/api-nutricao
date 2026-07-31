package br.com.nutricao.services.exception;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.nutricao.services.exception.Problema.Campo;

class ProblemaTest {

    @Test
    void deveConstruirComBuilder() {
        Problema problema = Problema.builder()
                .status(404)
                .titulo("Recurso nao encontrado")
                .detalhe("Usuario nao encontrado: 1")
                .build();

        assertEquals(404, problema.getStatus());
        assertEquals("Recurso nao encontrado", problema.getTitulo());
        assertEquals("Usuario nao encontrado: 1", problema.getDetalhe());
        assertNull(problema.getTipo());
        assertNull(problema.getMensagemUsuario());
        assertNull(problema.getCampos());
        assertNull(problema.getHoraDataErro());
    }

    @Test
    void deveConstruirComTodosOsCampos() {
        Campo campo = Campo.builder()
                .nome("email")
                .mensagem("Email ja cadastrado")
                .build();
        List<Campo> campos = Arrays.asList(campo);

        Problema problema = Problema.builder()
                .status(422)
                .tipo("https://api-nutricao.local/erro-negocio")
                .titulo("Violacao de regra de negocio")
                .detalhe("Email ja cadastrado: joao@email.com")
                .mensagemUsuario("Email ja cadastrado")
                .campos(campos)
                .build();

        assertEquals(422, problema.getStatus());
        assertEquals("https://api-nutricao.local/erro-negocio", problema.getTipo());
        assertEquals("Violacao de regra de negocio", problema.getTitulo());
        assertEquals("Email ja cadastrado: joao@email.com", problema.getDetalhe());
        assertEquals("Email ja cadastrado", problema.getMensagemUsuario());
        assertNotNull(problema.getCampos());
        assertEquals(1, problema.getCampos().size());
        assertEquals("email", problema.getCampos().get(0).getNome());
        assertEquals("Email ja cadastrado", problema.getCampos().get(0).getMensagem());
    }

    @Test
    void campoDeveConstruirComBuilder() {
        Campo campo = Campo.builder()
                .nome("nome")
                .mensagem("Nome e obrigatorio")
                .build();

        assertEquals("nome", campo.getNome());
        assertEquals("Nome e obrigatorio", campo.getMensagem());
    }
}
