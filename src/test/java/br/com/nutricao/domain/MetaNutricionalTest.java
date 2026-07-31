package br.com.nutricao.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class MetaNutricionalTest {

    @Test
    void criarMetaNutricional_ComDadosValidos_DeveInstanciarCorretamente() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        MetaNutricional meta = new MetaNutricional();
        meta.setUsuario(usuario);
        meta.setCalorias(new BigDecimal("2000"));
        meta.setProteinas(new BigDecimal("150"));
        meta.setCarboidratos(new BigDecimal("250"));
        meta.setGorduras(new BigDecimal("50"));
        meta.setDataInicio(LocalDate.of(2024, 1, 1));

        assertNotNull(meta);
        assertEquals(usuario, meta.getUsuario());
        assertEquals(0, new BigDecimal("2000").compareTo(meta.getCalorias()));
        assertEquals(0, new BigDecimal("150").compareTo(meta.getProteinas()));
        assertEquals(0, new BigDecimal("250").compareTo(meta.getCarboidratos()));
        assertEquals(0, new BigDecimal("50").compareTo(meta.getGorduras()));
        assertEquals(LocalDate.of(2024, 1, 1), meta.getDataInicio());
    }

    @Test
    void equals_ComMesmoId_DeveSerTrue() {
        MetaNutricional meta1 = new MetaNutricional();
        meta1.setId(1);
        MetaNutricional meta2 = new MetaNutricional();
        meta2.setId(1);

        assertEquals(meta1, meta2);
        assertEquals(meta1.hashCode(), meta2.hashCode());
    }

    @Test
    void equals_ComIdsDiferentes_DeveSerFalse() {
        MetaNutricional meta1 = new MetaNutricional();
        meta1.setId(1);
        MetaNutricional meta2 = new MetaNutricional();
        meta2.setId(2);

        assertNotEquals(meta1, meta2);
    }

    @Test
    void equals_ComIdNull_DeveSerTrue() {
        MetaNutricional meta1 = new MetaNutricional();
        MetaNutricional meta2 = new MetaNutricional();

        assertEquals(meta1, meta2);
        assertEquals(meta1.hashCode(), meta2.hashCode());
    }
}
