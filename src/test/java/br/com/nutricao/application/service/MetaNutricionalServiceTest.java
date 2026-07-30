package br.com.nutricao.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.nutricao.domain.model.MetaNutricional;
import br.com.nutricao.domain.model.Usuario;
import br.com.nutricao.infrastructure.persistence.MetaNutricionalRepository;

@ExtendWith(MockitoExtension.class)
class MetaNutricionalServiceTest {

    @Mock
    private MetaNutricionalRepository metaNutricionalRepository;

    @InjectMocks
    private MetaNutricionalService metaNutricionalService;

    private MetaNutricional meta;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        meta = new MetaNutricional();
        meta.setId(1);
        meta.setUsuario(usuario);
        meta.setCalorias(new BigDecimal("2500"));
        meta.setDataInicio(LocalDate.now());
    }

    @Test
    void criar_DeveSalvar() {
        when(metaNutricionalRepository.save(meta)).thenReturn(meta);

        MetaNutricional result = metaNutricionalService.criar(meta);

        assertNotNull(result);
        assertEquals(0, new BigDecimal("2500").compareTo(result.getCalorias()));
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar() {
        when(metaNutricionalRepository.findById(1)).thenReturn(Optional.of(meta));

        Optional<MetaNutricional> result = metaNutricionalService.buscarPorId(1);

        assertTrue(result.isPresent());
    }

    @Test
    void buscarPorUsuario_DeveRetornar() {
        when(metaNutricionalRepository.findByUsuarioId(1)).thenReturn(Arrays.asList(meta));

        List<MetaNutricional> result = metaNutricionalService.buscarPorUsuario(1);

        assertEquals(1, result.size());
    }

    @Test
    void buscarUltimaPorUsuario_DeveRetornar() {
        when(metaNutricionalRepository.findFirstByUsuarioIdOrderByDataInicioDesc(1))
                .thenReturn(Optional.of(meta));

        Optional<MetaNutricional> result = metaNutricionalService.buscarUltimaPorUsuario(1);

        assertTrue(result.isPresent());
    }

    @Test
    void atualizar_QuandoExistir_DeveAtualizar() {
        MetaNutricional atualizada = new MetaNutricional();
        atualizada.setCalorias(new BigDecimal("3000"));

        when(metaNutricionalRepository.existsById(1)).thenReturn(true);
        when(metaNutricionalRepository.save(any())).thenReturn(atualizada);

        MetaNutricional result = metaNutricionalService.atualizar(1, atualizada);

        assertEquals(0, new BigDecimal("3000").compareTo(result.getCalorias()));
    }

    @Test
    void deletar_QuandoExistir_DeveRemover() {
        when(metaNutricionalRepository.existsById(1)).thenReturn(true);

        metaNutricionalService.deletar(1);

        verify(metaNutricionalRepository).deleteById(1);
    }
}
