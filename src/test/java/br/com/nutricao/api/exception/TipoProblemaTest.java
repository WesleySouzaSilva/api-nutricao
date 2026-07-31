package br.com.nutricao.api.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TipoProblemaTest {

    @Test
    void deveTerUriCorreta() {
        assertEquals("https://api-nutricao.local/recurso-nao-encontrado", TipoProblema.RECURSO_NAO_ENCONTRADO.getUri());
        assertEquals("https://api-nutricao.local/entidade-em-uso", TipoProblema.ENTIDADE_EM_USO.getUri());
        assertEquals("https://api-nutricao.local/dados-invalidos", TipoProblema.DADOS_INVALIDOS.getUri());
        assertEquals("https://api-nutricao.local/erro-negocio", TipoProblema.ERRO_NEGOCIO.getUri());
        assertEquals("https://api-nutricao.local/erro-de-sistema", TipoProblema.ERRO_DE_SISTEMA.getUri());
        assertEquals("https://api-nutricao.local/parametro-invalido", TipoProblema.PARAMETRO_INVALIDO.getUri());
        assertEquals("https://api-nutricao.local/mensagem-incompreensivel", TipoProblema.MENSAGEM_INCOMPREENSIVEL.getUri());
    }

    @Test
    void deveTerTituloCorreto() {
        assertEquals("Recurso nao encontrado", TipoProblema.RECURSO_NAO_ENCONTRADO.getTitulo());
        assertEquals("Entidade em uso", TipoProblema.ENTIDADE_EM_USO.getTitulo());
        assertEquals("Dados invalidos", TipoProblema.DADOS_INVALIDOS.getTitulo());
        assertEquals("Violacao de regra de negocio", TipoProblema.ERRO_NEGOCIO.getTitulo());
        assertEquals("Erro de sistema", TipoProblema.ERRO_DE_SISTEMA.getTitulo());
        assertEquals("Parametro invalido", TipoProblema.PARAMETRO_INVALIDO.getTitulo());
        assertEquals("Mensagem incompreensivel", TipoProblema.MENSAGEM_INCOMPREENSIVEL.getTitulo());
    }

    @Test
    void deveTerSeteValores() {
        assertEquals(7, TipoProblema.values().length);
    }
}
