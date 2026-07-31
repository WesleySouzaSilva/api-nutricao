package br.com.nutricao.services.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.nutricao.services.exception.entidades.EntidadeEmUsoException;
import br.com.nutricao.services.exception.entidades.EntidadeNaoEncontradaException;
import br.com.nutricao.services.exception.NegocioException;
import br.com.nutricao.services.exception.Problema;
import br.com.nutricao.services.exception.TipoProblema;
import br.com.nutricao.services.exception.Problema.ProblemaBuilder;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA = "Ocorreu um erro interno inesperado no sistema. "
            + "Tente novamente e se o problema persistir, entre em contato com o administrador.";

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        TipoProblema tipoProblema = TipoProblema.RECURSO_NAO_ENCONTRADO;

        Problema problema = createProblemBuilder(status, tipoProblema, ex.getMessage()).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        TipoProblema tipoProblema = TipoProblema.ENTIDADE_EM_USO;

        Problema problema = createProblemBuilder(status, tipoProblema, ex.getMessage())
                .mensagemUsuario(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        TipoProblema tipoProblema = TipoProblema.ERRO_NEGOCIO;

        Problema problema = createProblemBuilder(status, tipoProblema, ex.getMessage())
                .mensagemUsuario(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        TipoProblema tipoProblema = TipoProblema.ENTIDADE_EM_USO;
        String detalhe = "Operacao nao pode ser concluida pois o registro esta relacionado a outros dados.";

        Problema problema = createProblemBuilder(status, tipoProblema, detalhe)
                .mensagemUsuario(detalhe)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        TipoProblema tipoProblema = TipoProblema.DADOS_INVALIDOS;

        Problema problema = createProblemBuilder(status, tipoProblema, ex.getMessage())
                .mensagemUsuario(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        TipoProblema tipoProblema = TipoProblema.ERRO_DE_SISTEMA;

        ex.printStackTrace();

        Problema problema = createProblemBuilder(status, tipoProblema, MSG_ERRO_GENERICA).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        TipoProblema tipoProblema = TipoProblema.DADOS_INVALIDOS;
        String detalhe = "Um ou mais campos estao invalidos. Faca o preenchimento correto e tente novamente.";

        List<Problema.Campo> campos = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> Problema.Campo.builder()
                        .nome(fieldError.getField())
                        .mensagem(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        Problema problema = createProblemBuilder(status, tipoProblema, detalhe)
                .mensagemUsuario(detalhe)
                .campos(campos)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
        String detalhe = "O corpo da requisicao esta invalido. Verifique erro de sintaxe.";

        Problema problema = createProblemBuilder(status, tipoProblema, detalhe)
                .mensagemUsuario(detalhe)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        if (body == null) {
            body = Problema.builder()
                    .status(status.value())
                    .titulo(status.getReasonPhrase())
                    .horaDataErro(LocalDateTime.now())
                    .build();
        } else if (body instanceof String) {
            body = Problema.builder()
                    .status(status.value())
                    .titulo((String) body)
                    .horaDataErro(LocalDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ProblemaBuilder createProblemBuilder(HttpStatus status, TipoProblema tipoProblema, String detalhe) {
        return Problema.builder()
                .status(status.value())
                .horaDataErro(LocalDateTime.now())
                .tipo(tipoProblema.getUri())
                .titulo(tipoProblema.getTitulo())
                .detalhe(detalhe);
    }
}
