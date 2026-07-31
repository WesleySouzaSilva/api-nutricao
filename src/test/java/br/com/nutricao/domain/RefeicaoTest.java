package br.com.nutricao.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class RefeicaoTest {

    @Test
    void criarRefeicao_ComDadosValidos_DeveInstanciarCorretamente() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        Refeicao refeicao = new Refeicao();
        refeicao.setNome("CAFE_DA_MANHA");
        refeicao.setDataRefeicao(LocalDateTime.now());
        refeicao.setUsuario(usuario);

        assertNotNull(refeicao);
        assertEquals("CAFE_DA_MANHA", refeicao.getNome());
        assertNotNull(refeicao.getDataRefeicao());
        assertEquals(usuario, refeicao.getUsuario());
    }

    @Test
    void equals_ComMesmoId_DeveSerTrue() {
        Refeicao refeicao1 = new Refeicao();
        refeicao1.setId(1);
        Refeicao refeicao2 = new Refeicao();
        refeicao2.setId(1);

        assertEquals(refeicao1, refeicao2);
        assertEquals(refeicao1.hashCode(), refeicao2.hashCode());
    }

    @Test
    void equals_ComIdsDiferentes_DeveSerFalse() {
        Refeicao refeicao1 = new Refeicao();
        refeicao1.setId(1);
        Refeicao refeicao2 = new Refeicao();
        refeicao2.setId(2);

        assertNotEquals(refeicao1, refeicao2);
    }

    @Test
    void equals_ComIdNull_DeveSerTrue() {
        Refeicao refeicao1 = new Refeicao();
        Refeicao refeicao2 = new Refeicao();

        assertEquals(refeicao1, refeicao2);
        assertEquals(refeicao1.hashCode(), refeicao2.hashCode());
    }
}
