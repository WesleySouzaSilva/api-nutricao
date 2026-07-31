package br.com.nutricao.services.exception.entidades;

import br.com.nutricao.services.exception.NegocioException;

public class EntidadeEmUsoException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public EntidadeEmUsoException(String mensagem) {
        super(mensagem);
    }
}
