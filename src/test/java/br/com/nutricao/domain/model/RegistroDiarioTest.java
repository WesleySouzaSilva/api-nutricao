package br.com.nutricao.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class RegistroDiarioTest {

    @Test
    void criarRegistroDiario_ComDadosValidos_DeveInstanciarCorretamente() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        RegistroDiario registro = new RegistroDiario();
        registro.setUsuario(usuario);
        registro.setData(LocalDate.of(2024, 1, 15));
        registro.setCaloriasConsumidas(new BigDecimal("1850"));
        registro.setProteinasConsumidas(new BigDecimal("120"));
        registro.setCarboidratosConsumidos(new BigDecimal("220"));
        registro.setGordurasConsumidas(new BigDecimal("45"));

        assertNotNull(registro);
        assertEquals(usuario, registro.getUsuario());
        assertEquals(LocalDate.of(2024, 1, 15), registro.getData());
        assertEquals(0, new BigDecimal("1850").compareTo(registro.getCaloriasConsumidas()));
        assertEquals(0, new BigDecimal("120").compareTo(registro.getProteinasConsumidas()));
        assertEquals(0, new BigDecimal("220").compareTo(registro.getCarboidratosConsumidos()));
        assertEquals(0, new BigDecimal("45").compareTo(registro.getGordurasConsumidas()));
    }

    @Test
    void equals_ComMesmoId_DeveSerTrue() {
        RegistroDiario reg1 = new RegistroDiario();
        reg1.setId(1);
        RegistroDiario reg2 = new RegistroDiario();
        reg2.setId(1);

        assertEquals(reg1, reg2);
        assertEquals(reg1.hashCode(), reg2.hashCode());
    }

    @Test
    void equals_ComIdsDiferentes_DeveSerFalse() {
        RegistroDiario reg1 = new RegistroDiario();
        reg1.setId(1);
        RegistroDiario reg2 = new RegistroDiario();
        reg2.setId(2);

        assertNotEquals(reg1, reg2);
    }

    @Test
    void equals_ComIdNull_DeveSerTrue() {
        RegistroDiario reg1 = new RegistroDiario();
        RegistroDiario reg2 = new RegistroDiario();

        assertEquals(reg1, reg2);
        assertEquals(reg1.hashCode(), reg2.hashCode());
    }
}
