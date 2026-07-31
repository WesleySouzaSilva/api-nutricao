package br.com.nutricao.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CategoriaAlimentoTest {

    @Test
    void criarCategoria_ComDadosValidos_DeveInstanciarCorretamente() {
        CategoriaAlimento categoria = new CategoriaAlimento();
        categoria.setNome("Frutas");

        assertNotNull(categoria);
        assertEquals("Frutas", categoria.getNome());
    }

    @Test
    void equals_ComMesmoId_DeveSerTrue() {
        CategoriaAlimento categoria1 = new CategoriaAlimento();
        categoria1.setId(1);
        CategoriaAlimento categoria2 = new CategoriaAlimento();
        categoria2.setId(1);

        assertEquals(categoria1, categoria2);
        assertEquals(categoria1.hashCode(), categoria2.hashCode());
    }

    @Test
    void equals_ComIdsDiferentes_DeveSerFalse() {
        CategoriaAlimento categoria1 = new CategoriaAlimento();
        categoria1.setId(1);
        CategoriaAlimento categoria2 = new CategoriaAlimento();
        categoria2.setId(2);

        assertNotEquals(categoria1, categoria2);
    }

    @Test
    void equals_ComIdNull_DeveSerTrue() {
        CategoriaAlimento categoria1 = new CategoriaAlimento();
        CategoriaAlimento categoria2 = new CategoriaAlimento();

        assertEquals(categoria1, categoria2);
        assertEquals(categoria1.hashCode(), categoria2.hashCode());
    }
}
