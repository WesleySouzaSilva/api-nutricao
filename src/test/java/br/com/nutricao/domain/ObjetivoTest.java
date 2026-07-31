package br.com.nutricao.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ObjetivoTest {

    @Test
    void criarObjetivo_ComDadosValidos_DeveInstanciarCorretamente() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        Objetivo objetivo = new Objetivo();
        objetivo.setUsuario(usuario);
        objetivo.setTipo("PERDER_PESO");
        objetivo.setPesoAlvo(new BigDecimal("70"));
        objetivo.setCaloriasDiarias(new BigDecimal("1800"));
        objetivo.setDataInicio(LocalDate.of(2024, 1, 1));

        assertNotNull(objetivo);
        assertEquals(usuario, objetivo.getUsuario());
        assertEquals("PERDER_PESO", objetivo.getTipo());
        assertEquals(0, new BigDecimal("70").compareTo(objetivo.getPesoAlvo()));
        assertEquals(0, new BigDecimal("1800").compareTo(objetivo.getCaloriasDiarias()));
        assertEquals(LocalDate.of(2024, 1, 1), objetivo.getDataInicio());
    }

    @Test
    void equals_ComMesmoId_DeveSerTrue() {
        Objetivo obj1 = new Objetivo();
        obj1.setId(1);
        Objetivo obj2 = new Objetivo();
        obj2.setId(1);

        assertEquals(obj1, obj2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    void equals_ComIdsDiferentes_DeveSerFalse() {
        Objetivo obj1 = new Objetivo();
        obj1.setId(1);
        Objetivo obj2 = new Objetivo();
        obj2.setId(2);

        assertNotEquals(obj1, obj2);
    }

    @Test
    void equals_ComIdNull_DeveSerTrue() {
        Objetivo obj1 = new Objetivo();
        Objetivo obj2 = new Objetivo();

        assertEquals(obj1, obj2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }
}
