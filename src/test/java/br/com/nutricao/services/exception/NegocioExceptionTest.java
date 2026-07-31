package br.com.nutricao.services.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NegocioExceptionTest {

    @Test
    void deveArmazenarMensagem() {
        NegocioException ex = new NegocioException("Erro de negocio");

        assertEquals("Erro de negocio", ex.getMessage());
    }

    @Test
    void deveArmazenarMensagemECausa() {
        RuntimeException causa = new RuntimeException("Causa original");

        NegocioException ex = new NegocioException("Erro de negocio", causa);

        assertEquals("Erro de negocio", ex.getMessage());
        assertSame(causa, ex.getCause());
    }

    @Test
    void deveSerRuntimeException() {
        NegocioException ex = new NegocioException("Erro");

        assertTrue(ex instanceof RuntimeException);
    }
}
