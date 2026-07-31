package br.com.nutricao.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class AlimentoRefeicaoTest {

    @Test
    void criarAlimentoRefeicao_ComDadosValidos_DeveInstanciarCorretamente() {
        Refeicao refeicao = new Refeicao(1);
        Alimento alimento = new Alimento(1);

        AlimentoRefeicao item = new AlimentoRefeicao();
        item.setRefeicao(refeicao);
        item.setAlimento(alimento);
        item.setQuantidade(new BigDecimal("100"));
        item.setPorcao("1 porcao");

        assertNotNull(item);
        assertEquals(refeicao, item.getRefeicao());
        assertEquals(alimento, item.getAlimento());
        assertEquals(0, new BigDecimal("100").compareTo(item.getQuantidade()));
        assertEquals("1 porcao", item.getPorcao());
    }

    @Test
    void equals_ComMesmoId_DeveSerTrue() {
        AlimentoRefeicao item1 = new AlimentoRefeicao();
        item1.setId(1);
        AlimentoRefeicao item2 = new AlimentoRefeicao();
        item2.setId(1);

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void equals_ComIdsDiferentes_DeveSerFalse() {
        AlimentoRefeicao item1 = new AlimentoRefeicao();
        item1.setId(1);
        AlimentoRefeicao item2 = new AlimentoRefeicao();
        item2.setId(2);

        assertNotEquals(item1, item2);
    }

    @Test
    void equals_ComIdNull_DeveSerTrue() {
        AlimentoRefeicao item1 = new AlimentoRefeicao();
        AlimentoRefeicao item2 = new AlimentoRefeicao();

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
    }
}
