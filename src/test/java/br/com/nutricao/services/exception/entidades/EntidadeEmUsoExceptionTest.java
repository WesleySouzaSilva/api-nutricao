package br.com.nutricao.services.exception.entidades;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.com.nutricao.services.exception.NegocioException;

class EntidadeEmUsoExceptionTest {

    @Test
    void deveArmazenarMensagem() {
        EntidadeEmUsoException ex = new EntidadeEmUsoException(
                "Categoria nao pode ser removida pois esta em uso por alimentos");

        assertEquals("Categoria nao pode ser removida pois esta em uso por alimentos", ex.getMessage());
    }

    @Test
    void deveEstenderNegocioException() {
        EntidadeEmUsoException ex = new EntidadeEmUsoException("Entidade em uso");

        assertTrue(ex instanceof NegocioException);
        assertTrue(ex instanceof RuntimeException);
    }
}
