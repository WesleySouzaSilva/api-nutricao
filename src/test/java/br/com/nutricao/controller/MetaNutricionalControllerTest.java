package br.com.nutricao.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.nutricao.services.exception.entidades.EntidadeNaoEncontradaException;
import br.com.nutricao.domain.dto.filtro.MetaNutricionalCamposFiltro;
import br.com.nutricao.domain.dto.insercao.MetaNutricionalRequest;
import br.com.nutricao.services.MetaNutricionalService;
import br.com.nutricao.domain.MetaNutricional;
import br.com.nutricao.domain.Usuario;

@WebMvcTest(MetaNutricionalController.class)
@AutoConfigureMockMvc(addFilters = false)
class MetaNutricionalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MetaNutricionalService metaNutricionalService;

    private MetaNutricional meta;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        meta = new MetaNutricional();
        meta.setId(1);
        meta.setUsuario(usuario);
        meta.setCalorias(new BigDecimal("2000"));
        meta.setProteinas(new BigDecimal("100"));
        meta.setCarboidratos(new BigDecimal("200"));
        meta.setGorduras(new BigDecimal("50"));
        meta.setDataInicio(LocalDate.now());
    }

    @Test
    void criar_DeveRetornar201() throws Exception {
        MetaNutricionalRequest request = new MetaNutricionalRequest();
        request.setUsuarioId(1);
        request.setCalorias(new BigDecimal("2000"));

        when(metaNutricionalService.criar(any(MetaNutricional.class))).thenReturn(meta);

        mockMvc.perform(post("/api/v1/metas-nutricionais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void listar_DeveRetornar200() throws Exception {
        Page<MetaNutricional> page = new PageImpl<>(Arrays.asList(meta));
        when(metaNutricionalService.listarTodosFiltro(any(MetaNutricionalCamposFiltro.class), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/metas-nutricionais"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1));
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar200() throws Exception {
        when(metaNutricionalService.buscarPorId(1)).thenReturn(Optional.of(meta));

        mockMvc.perform(get("/api/v1/metas-nutricionais/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornar404() throws Exception {
        when(metaNutricionalService.buscarPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/metas-nutricionais/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorUsuario_DeveRetornar200() throws Exception {
        when(metaNutricionalService.buscarPorUsuario(1)).thenReturn(Arrays.asList(meta));

        mockMvc.perform(get("/api/v1/metas-nutricionais/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void buscarUltimaPorUsuario_QuandoExistir_DeveRetornar200() throws Exception {
        when(metaNutricionalService.buscarUltimaPorUsuario(1)).thenReturn(Optional.of(meta));

        mockMvc.perform(get("/api/v1/metas-nutricionais/usuario/1/ultima"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarUltimaPorUsuario_QuandoNaoExistir_DeveRetornar404() throws Exception {
        when(metaNutricionalService.buscarUltimaPorUsuario(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/metas-nutricionais/usuario/99/ultima"))
                .andExpect(status().isNotFound());
    }

    @Test
    void atualizar_DeveRetornar200() throws Exception {
        MetaNutricionalRequest request = new MetaNutricionalRequest();
        request.setCalorias(new BigDecimal("2500"));

        MetaNutricional updated = new MetaNutricional();
        updated.setId(1);
        updated.setCalorias(new BigDecimal("2500"));

        when(metaNutricionalService.atualizar(eq(1), any(MetaNutricional.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/metas-nutricionais/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calorias").value(2500));
    }

    @Test
    void deletar_DeveRetornar204() throws Exception {
        doNothing().when(metaNutricionalService).deletar(1);

        mockMvc.perform(delete("/api/v1/metas-nutricionais/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void listar_QuandoVazio_DeveRetornar200() throws Exception {
        Page<MetaNutricional> page = new PageImpl<>(java.util.Collections.emptyList());
        when(metaNutricionalService.listarTodosFiltro(any(MetaNutricionalCamposFiltro.class), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/metas-nutricionais"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    void atualizar_QuandoNaoExistir_DeveRetornar404() throws Exception {
        MetaNutricionalRequest request = new MetaNutricionalRequest();
        request.setCalorias(new BigDecimal("2500"));

        when(metaNutricionalService.atualizar(eq(99), any(MetaNutricional.class)))
                .thenThrow(new EntidadeNaoEncontradaException("MetaNutricional nao encontrada: 99"));

        mockMvc.perform(put("/api/v1/metas-nutricionais/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletar_QuandoNaoExistir_DeveRetornar404() throws Exception {
        doThrow(new EntidadeNaoEncontradaException("MetaNutricional nao encontrada: 99"))
                .when(metaNutricionalService).deletar(99);

        mockMvc.perform(delete("/api/v1/metas-nutricionais/99"))
                .andExpect(status().isNotFound());
    }
}
