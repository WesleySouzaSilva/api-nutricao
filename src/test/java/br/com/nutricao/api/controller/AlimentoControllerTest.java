package br.com.nutricao.api.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.nutricao.application.dto.AlimentoRequest;
import br.com.nutricao.application.dto.AlimentoResponse;
import br.com.nutricao.application.dto.CategoriaAlimentoResponse;
import br.com.nutricao.application.service.AlimentoService;
import br.com.nutricao.domain.model.Alimento;
import br.com.nutricao.domain.model.CategoriaAlimento;

@WebMvcTest(AlimentoController.class)
@AutoConfigureMockMvc(addFilters = false)
class AlimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AlimentoService alimentoService;

    private Alimento alimento;

    @BeforeEach
    void setUp() {
        CategoriaAlimento cat = new CategoriaAlimento();
        cat.setId(1);
        cat.setNome("Frutas");

        alimento = new Alimento();
        alimento.setId(1);
        alimento.setNome("Banana");
        alimento.setCategoriaAlimento(cat);
        alimento.setKcal("89");
        alimento.setProteina("1.1");
        alimento.setGordura("0.3");
        alimento.setCarboidrato("23");
    }

    @Test
    void criar_DeveRetornar201() throws Exception {
        AlimentoRequest request = new AlimentoRequest();
        request.setNome("Banana");
        request.setCategoriaAlimentoId(1);
        request.setKcal("89");

        when(alimentoService.criar(any(Alimento.class))).thenReturn(alimento);

        mockMvc.perform(post("/api/v1/alimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Banana"));
    }

    @Test
    void listar_SemFiltros_DeveRetornar200() throws Exception {
        when(alimentoService.listar()).thenReturn(Arrays.asList(alimento));

        mockMvc.perform(get("/api/v1/alimentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void listar_ComCategoriaId_DeveRetornar200() throws Exception {
        when(alimentoService.buscarPorCategoria(1)).thenReturn(Arrays.asList(alimento));

        mockMvc.perform(get("/api/v1/alimentos").param("categoriaId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void listar_ComNome_DeveRetornar200() throws Exception {
        when(alimentoService.buscarPorNome("Banana")).thenReturn(Arrays.asList(alimento));

        mockMvc.perform(get("/api/v1/alimentos").param("nome", "Banana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar200() throws Exception {
        when(alimentoService.buscarPorId(1)).thenReturn(Optional.of(alimento));

        mockMvc.perform(get("/api/v1/alimentos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornar404() throws Exception {
        when(alimentoService.buscarPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/alimentos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void atualizar_DeveRetornar200() throws Exception {
        AlimentoRequest request = new AlimentoRequest();
        request.setNome("Banana Prata");
        request.setKcal("92");

        Alimento updated = new Alimento();
        updated.setId(1);
        updated.setNome("Banana Prata");
        updated.setKcal("92");

        when(alimentoService.atualizar(eq(1), any(Alimento.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/alimentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Banana Prata"));
    }

    @Test
    void deletar_DeveRetornar204() throws Exception {
        doNothing().when(alimentoService).deletar(1);

        mockMvc.perform(delete("/api/v1/alimentos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void listar_SemFiltros_QuandoVazio_DeveRetornar200() throws Exception {
        when(alimentoService.listar()).thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/api/v1/alimentos"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void listar_ComCategoriaId_QuandoVazio_DeveRetornar200() throws Exception {
        when(alimentoService.buscarPorCategoria(99)).thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/api/v1/alimentos").param("categoriaId", "99"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void listar_ComNome_QuandoNaoExistir_DeveRetornar200() throws Exception {
        when(alimentoService.buscarPorNome("NAOEXISTE")).thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/api/v1/alimentos").param("nome", "NAOEXISTE"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void atualizar_QuandoNaoExistir_DeveRetornar400() throws Exception {
        AlimentoRequest request = new AlimentoRequest();
        request.setNome("Banana");

        when(alimentoService.atualizar(eq(99), any(Alimento.class)))
                .thenThrow(new IllegalArgumentException("Alimento nao encontrado: 99"));

        mockMvc.perform(put("/api/v1/alimentos/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletar_QuandoNaoExistir_DeveRetornar400() throws Exception {
        doThrow(new IllegalArgumentException("Alimento nao encontrado: 99"))
                .when(alimentoService).deletar(99);

        mockMvc.perform(delete("/api/v1/alimentos/99"))
                .andExpect(status().isBadRequest());
    }
}
