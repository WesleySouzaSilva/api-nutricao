package br.com.nutricao.services.exception;

import lombok.Getter;

@Getter
public enum TipoProblema {

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso nao encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados invalidos"),
    ERRO_NEGOCIO("/erro-negocio", "Violacao de regra de negocio"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parametro invalido"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel");

    private final String uri;
    private final String titulo;

    TipoProblema(String path, String titulo) {
        this.uri = "https://api-nutricao.local" + path;
        this.titulo = titulo;
    }
}
