package br.com.nutricao.api.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.nutricao.application.dto.CategoriaAlimentoRequest;
import br.com.nutricao.application.service.CategoriaAlimentoService;
import br.com.nutricao.domain.model.CategoriaAlimento;

@WebMvcTest(CategoriaAlimentoController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoriaAlimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoriaAlimentoService categoriaAlimentoService;

    @Test
    void criar_DeveRetornar201() throws Exception {
        CategoriaAlimentoRequest request = new CategoriaAlimentoRequest();
        request.setNome("Frutas");

        CategoriaAlimento saved = new CategoriaAlimento(1, "Frutas");

        when(categoriaAlimentoService.criar(any(CategoriaAlimento.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Frutas"));
    }

    @Test
    void listar_DeveRetornar200() throws Exception {
        when(categoriaAlimentoService.listar()).thenReturn(Arrays.asList(new CategoriaAlimento(1, "Frutas")));

        mockMvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar200() throws Exception {
        when(categoriaAlimentoService.buscarPorId(1)).thenReturn(Optional.of(new CategoriaAlimento(1, "Frutas")));

        mockMvc.perform(get("/api/v1/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornar404() throws Exception {
        when(categoriaAlimentoService.buscarPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/categorias/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void atualizar_DeveRetornar200() throws Exception {
        CategoriaAlimentoRequest request = new CategoriaAlimentoRequest();
        request.setNome("Legumes");

        when(categoriaAlimentoService.atualizar(eq(1), any(CategoriaAlimento.class)))
                .thenReturn(new CategoriaAlimento(1, "Legumes"));

        mockMvc.perform(put("/api/v1/categorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Legumes"));
    }

    @Test
    void deletar_DeveRetornar204() throws Exception {
        doNothing().when(categoriaAlimentoService).deletar(1);

        mockMvc.perform(delete("/api/v1/categorias/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void criar_ComNomeDuplicado_DeveRetornar400() throws Exception {
        CategoriaAlimentoRequest request = new CategoriaAlimentoRequest();
        request.setNome("Frutas");

        when(categoriaAlimentoService.criar(any(CategoriaAlimento.class)))
                .thenThrow(new IllegalArgumentException("Categoria ja existente: Frutas"));

        mockMvc.perform(post("/api/v1/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listar_QuandoVazio_DeveRetornar200() throws Exception {
        when(categoriaAlimentoService.listar()).thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void atualizar_QuandoNaoExistir_DeveRetornar400() throws Exception {
        CategoriaAlimentoRequest request = new CategoriaAlimentoRequest();
        request.setNome("Legumes");

        when(categoriaAlimentoService.atualizar(eq(99), any(CategoriaAlimento.class)))
                .thenThrow(new IllegalArgumentException("Categoria nao encontrada: 99"));

        mockMvc.perform(put("/api/v1/categorias/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletar_QuandoNaoExistir_DeveRetornar400() throws Exception {
        doThrow(new IllegalArgumentException("Categoria nao encontrada: 99"))
                .when(categoriaAlimentoService).deletar(99);

        mockMvc.perform(delete("/api/v1/categorias/99"))
                .andExpect(status().isBadRequest());
    }
}
