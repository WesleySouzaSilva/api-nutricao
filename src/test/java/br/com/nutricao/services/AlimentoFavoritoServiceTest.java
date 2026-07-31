package br.com.nutricao.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.nutricao.domain.Alimento;
import br.com.nutricao.domain.AlimentoFavorito;
import br.com.nutricao.domain.Usuario;
import br.com.nutricao.repositories.AlimentoFavoritoRepository;

@ExtendWith(MockitoExtension.class)
class AlimentoFavoritoServiceTest {

    @Mock
    private AlimentoFavoritoRepository alimentoFavoritoRepository;

    @InjectMocks
    private AlimentoFavoritoService alimentoFavoritoService;

    private AlimentoFavorito favorito;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        Alimento alimento = new Alimento();
        alimento.setId(1);
        alimento.setNome("Maca");

        favorito = new AlimentoFavorito();
        favorito.setId(1);
        favorito.setUsuario(usuario);
        favorito.setAlimento(alimento);
        favorito.setDataAdicao(LocalDateTime.now());
    }

    @Test
    void adicionar_DeveSalvar() {
        when(alimentoFavoritoRepository.save(favorito)).thenReturn(favorito);

        AlimentoFavorito result = alimentoFavoritoService.adicionar(favorito);

        assertNotNull(result);
    }

    @Test
    void buscarPorUsuario_DeveRetornar() {
        when(alimentoFavoritoRepository.findByUsuarioIdOrderByDataAdicaoDesc(1))
                .thenReturn(Arrays.asList(favorito));

        List<AlimentoFavorito> result = alimentoFavoritoService.buscarPorUsuario(1);

        assertEquals(1, result.size());
        assertEquals("Maca", result.get(0).getAlimento().getNome());
    }

    @Test
    void existePorUsuarioEAlimento_DeveRetornarTrue() {
        when(alimentoFavoritoRepository.existsByUsuarioIdAndAlimentoId(1, 1)).thenReturn(true);

        boolean result = alimentoFavoritoService.existePorUsuarioEAlimento(1, 1);

        assertTrue(result);
    }

    @Test
    void existePorUsuarioEAlimento_QuandoNaoExistir_DeveRetornarFalse() {
        when(alimentoFavoritoRepository.existsByUsuarioIdAndAlimentoId(99, 99)).thenReturn(false);

        boolean result = alimentoFavoritoService.existePorUsuarioEAlimento(99, 99);

        assertFalse(result);
    }

    @Test
    void remover_DeveChamarDelete() {
        alimentoFavoritoService.remover(1, 1);

        verify(alimentoFavoritoRepository).deleteByUsuarioIdAndAlimentoId(1, 1);
    }
}
