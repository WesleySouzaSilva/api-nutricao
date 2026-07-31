package br.com.nutricao.services.exception;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import br.com.nutricao.services.exception.entidades.EntidadeEmUsoException;
import br.com.nutricao.services.exception.entidades.EntidadeNaoEncontradaException;
import br.com.nutricao.services.exception.NegocioException;
import br.com.nutricao.services.exception.Problema;

class ApiExceptionHandlerTest {

    private ApiExceptionHandler handler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        handler = new ApiExceptionHandler();
        webRequest = mock(WebRequest.class);
    }

    @Test
    void handleEntidadeNaoEncontrada_DeveRetornar404() {
        EntidadeNaoEncontradaException ex = new EntidadeNaoEncontradaException("Usuario nao encontrado: 1");

        ResponseEntity<Object> response = handler.handleEntidadeNaoEncontrada(ex, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Problema problema = (Problema) response.getBody();
        assertNotNull(problema);
        assertEquals(404, problema.getStatus());
        assertEquals("Recurso nao encontrado", problema.getTitulo());
        assertEquals("Usuario nao encontrado: 1", problema.getDetalhe());
        assertEquals("https://api-nutricao.local/recurso-nao-encontrado", problema.getTipo());
    }

    @Test
    void handleEntidadeEmUso_DeveRetornar409() {
        EntidadeEmUsoException ex = new EntidadeEmUsoException("Categoria em uso por alimentos");

        ResponseEntity<Object> response = handler.handleEntidadeEmUso(ex, webRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Problema problema = (Problema) response.getBody();
        assertNotNull(problema);
        assertEquals(409, problema.getStatus());
        assertEquals("Entidade em uso", problema.getTitulo());
        assertEquals("Categoria em uso por alimentos", problema.getDetalhe());
        assertEquals("Categoria em uso por alimentos", problema.getMensagemUsuario());
        assertEquals("https://api-nutricao.local/entidade-em-uso", problema.getTipo());
    }

    @Test
    void handleNegocio_DeveRetornar422() {
        NegocioException ex = new NegocioException("Email ja cadastrado");

        ResponseEntity<Object> response = handler.handleNegocio(ex, webRequest);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        Problema problema = (Problema) response.getBody();
        assertNotNull(problema);
        assertEquals(422, problema.getStatus());
        assertEquals("Violacao de regra de negocio", problema.getTitulo());
        assertEquals("Email ja cadastrado", problema.getDetalhe());
        assertEquals("Email ja cadastrado", problema.getMensagemUsuario());
        assertEquals("https://api-nutricao.local/erro-negocio", problema.getTipo());
    }

    @Test
    void handleDataIntegrityViolation_DeveRetornar409() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Constraint violada");

        ResponseEntity<Object> response = handler.handleDataIntegrityViolation(ex, webRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Problema problema = (Problema) response.getBody();
        assertNotNull(problema);
        assertEquals(409, problema.getStatus());
        assertEquals("Entidade em uso", problema.getTitulo());
        assertEquals("Operacao nao pode ser concluida pois o registro esta relacionado a outros dados.",
                problema.getDetalhe());
        assertEquals("https://api-nutricao.local/entidade-em-uso", problema.getTipo());
    }

    @Test
    void handleIllegalArgument_DeveRetornar400() {
        IllegalArgumentException ex = new IllegalArgumentException("Parametro invalido");

        ResponseEntity<Object> response = handler.handleIllegalArgument(ex, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Problema problema = (Problema) response.getBody();
        assertNotNull(problema);
        assertEquals(400, problema.getStatus());
        assertEquals("Dados invalidos", problema.getTitulo());
        assertEquals("Parametro invalido", problema.getDetalhe());
        assertEquals("https://api-nutricao.local/dados-invalidos", problema.getTipo());
    }

    @Test
    void handleUncaught_DeveRetornar500ComMensagemGenerica() {
        Exception ex = new Exception("Erro interno");

        ResponseEntity<Object> response = handler.handleUncaught(ex, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Problema problema = (Problema) response.getBody();
        assertNotNull(problema);
        assertEquals(500, problema.getStatus());
        assertEquals("Erro de sistema", problema.getTitulo());
        assertEquals(ApiExceptionHandler.MSG_ERRO_GENERICA, problema.getDetalhe());
        assertEquals("https://api-nutricao.local/erro-de-sistema", problema.getTipo());
    }

    @Test
    void handleMethodArgumentNotValid_DeveRetornar400ComCampos() throws Exception {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(
                new Object(), "objeto");
        bindingResult.addError(new FieldError("objeto", "nome", "Nome e obrigatorio"));
        bindingResult.addError(new FieldError("objeto", "email", "Email invalido"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(
                mock(MethodParameter.class), bindingResult);

        ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(
                ex, null, HttpStatus.BAD_REQUEST, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Problema problema = (Problema) response.getBody();
        assertNotNull(problema);
        assertEquals("Dados invalidos", problema.getTitulo());
        assertEquals("Um ou mais campos estao invalidos. Faca o preenchimento correto e tente novamente.",
                problema.getDetalhe());
        assertEquals("https://api-nutricao.local/dados-invalidos", problema.getTipo());

        List<Problema.Campo> campos = problema.getCampos();
        assertNotNull(campos);
        assertEquals(2, campos.size());
        assertEquals("nome", campos.get(0).getNome());
        assertEquals("Nome e obrigatorio", campos.get(0).getMensagem());
        assertEquals("email", campos.get(1).getNome());
        assertEquals("Email invalido", campos.get(1).getMensagem());
    }

    @Test
    void handleHttpMessageNotReadable_DeveRetornar400() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("JSON malformado");

        ResponseEntity<Object> response = handler.handleHttpMessageNotReadable(
                ex, null, HttpStatus.BAD_REQUEST, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Problema problema = (Problema) response.getBody();
        assertNotNull(problema);
        assertEquals("Mensagem incompreensivel", problema.getTitulo());
        assertEquals("O corpo da requisicao esta invalido. Verifique erro de sintaxe.",
                problema.getDetalhe());
        assertEquals("https://api-nutricao.local/mensagem-incompreensivel", problema.getTipo());
    }

    @Test
    void msgErroGenerica_DeveTerValorDefinido() {
        assertNotNull(ApiExceptionHandler.MSG_ERRO_GENERICA);
        assertFalse(ApiExceptionHandler.MSG_ERRO_GENERICA.isEmpty());
    }
}
