package br.com.nutricao.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class AlimentoFavoritoTest {

    @Test
    void criarAlimentoFavorito_ComDadosValidos_DeveInstanciarCorretamente() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        Alimento alimento = new Alimento(1);

        AlimentoFavorito favorito = new AlimentoFavorito();
        favorito.setUsuario(usuario);
        favorito.setAlimento(alimento);
        favorito.setDataAdicao(LocalDateTime.now());

        assertNotNull(favorito);
        assertEquals(usuario, favorito.getUsuario());
        assertEquals(alimento, favorito.getAlimento());
        assertNotNull(favorito.getDataAdicao());
    }

    @Test
    void equals_ComMesmoId_DeveSerTrue() {
        AlimentoFavorito fav1 = new AlimentoFavorito();
        fav1.setId(1);
        AlimentoFavorito fav2 = new AlimentoFavorito();
        fav2.setId(1);

        assertEquals(fav1, fav2);
        assertEquals(fav1.hashCode(), fav2.hashCode());
    }

    @Test
    void equals_ComIdsDiferentes_DeveSerFalse() {
        AlimentoFavorito fav1 = new AlimentoFavorito();
        fav1.setId(1);
        AlimentoFavorito fav2 = new AlimentoFavorito();
        fav2.setId(2);

        assertNotEquals(fav1, fav2);
    }

    @Test
    void equals_ComIdNull_DeveSerTrue() {
        AlimentoFavorito fav1 = new AlimentoFavorito();
        AlimentoFavorito fav2 = new AlimentoFavorito();

        assertEquals(fav1, fav2);
        assertEquals(fav1.hashCode(), fav2.hashCode());
    }
}
