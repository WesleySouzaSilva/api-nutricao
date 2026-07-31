package br.com.nutricao.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AlimentoTest {

    @Test
    void criarAlimento_ComDadosValidos_DeveInstanciarCorretamente() {
        CategoriaAlimento categoria = new CategoriaAlimento(1);
        Alimento alimento = new Alimento();
        alimento.setNome("Arroz branco");
        alimento.setKcal("130");
        alimento.setProteina("2.5");
        alimento.setCarboidrato("28.0");
        alimento.setCategoriaAlimento(categoria);

        assertNotNull(alimento);
        assertEquals("Arroz branco", alimento.getNome());
        assertEquals("130", alimento.getKcal());
        assertEquals("2.5", alimento.getProteina());
        assertEquals("28.0", alimento.getCarboidrato());
        assertEquals(categoria, alimento.getCategoriaAlimento());
    }

    @Test
    void equals_ComMesmoId_DeveSerTrue() {
        CategoriaAlimento categoria = new CategoriaAlimento(1);
        Alimento alimento1 = new Alimento();
        alimento1.setId(1);
        alimento1.setCategoriaAlimento(categoria);
        Alimento alimento2 = new Alimento();
        alimento2.setId(1);
        alimento2.setCategoriaAlimento(categoria);

        assertEquals(alimento1, alimento2);
        assertEquals(alimento1.hashCode(), alimento2.hashCode());
    }

    @Test
    void equals_ComIdsDiferentes_DeveSerFalse() {
        Alimento alimento1 = new Alimento();
        alimento1.setId(1);
        Alimento alimento2 = new Alimento();
        alimento2.setId(2);

        assertNotEquals(alimento1, alimento2);
    }

    @Test
    void equals_ComIdNull_DeveSerTrue() {
        Alimento alimento1 = new Alimento();
        Alimento alimento2 = new Alimento();

        assertEquals(alimento1, alimento2);
        assertEquals(alimento1.hashCode(), alimento2.hashCode());
    }
}
