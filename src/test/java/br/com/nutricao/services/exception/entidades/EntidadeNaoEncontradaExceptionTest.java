package br.com.nutricao.services.exception.entidades;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.com.nutricao.services.exception.NegocioException;

class EntidadeNaoEncontradaExceptionTest {

    @Test
    void deveArmazenarMensagem() {
        EntidadeNaoEncontradaException ex = new EntidadeNaoEncontradaException("Usuario nao encontrado: 1");

        assertEquals("Usuario nao encontrado: 1", ex.getMessage());
    }

    @Test
    void deveEstenderNegocioException() {
        EntidadeNaoEncontradaException ex = new EntidadeNaoEncontradaException("Recurso nao encontrado");

        assertTrue(ex instanceof NegocioException);
        assertTrue(ex instanceof RuntimeException);
    }
}
