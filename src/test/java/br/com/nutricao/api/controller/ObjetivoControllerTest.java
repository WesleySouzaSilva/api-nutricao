package br.com.nutricao.api.controller;

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
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.nutricao.application.dto.ObjetivoRequest;
import br.com.nutricao.application.service.ObjetivoService;
import br.com.nutricao.domain.model.Objetivo;
import br.com.nutricao.domain.model.Usuario;

@WebMvcTest(ObjetivoController.class)
@AutoConfigureMockMvc(addFilters = false)
class ObjetivoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ObjetivoService objetivoService;

    private Objetivo objetivo;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        objetivo = new Objetivo();
        objetivo.setId(1);
        objetivo.setUsuario(usuario);
        objetivo.setTipo("EMAGRECER");
        objetivo.setDataInicio(LocalDate.now());
    }

    @Test
    void criar_DeveRetornar201() throws Exception {
        ObjetivoRequest request = new ObjetivoRequest();
        request.setUsuarioId(1);
        request.setTipo("EMAGRECER");
        request.setDataInicio(LocalDate.now());

        when(objetivoService.criar(any(Objetivo.class))).thenReturn(objetivo);

        mockMvc.perform(post("/api/v1/objetivos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tipo").value("EMAGRECER"));
    }

    @Test
    void listar_DeveRetornar200() throws Exception {
        when(objetivoService.listar()).thenReturn(Arrays.asList(objetivo));

        mockMvc.perform(get("/api/v1/objetivos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar200() throws Exception {
        when(objetivoService.buscarPorId(1)).thenReturn(Optional.of(objetivo));

        mockMvc.perform(get("/api/v1/objetivos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornar404() throws Exception {
        when(objetivoService.buscarPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/objetivos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorUsuario_DeveRetornar200() throws Exception {
        when(objetivoService.buscarPorUsuario(1)).thenReturn(Arrays.asList(objetivo));

        mockMvc.perform(get("/api/v1/objetivos/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void atualizar_DeveRetornar200() throws Exception {
        ObjetivoRequest request = new ObjetivoRequest();
        request.setTipo("HIPERTROFIA");

        Objetivo updated = new Objetivo();
        updated.setId(1);
        updated.setTipo("HIPERTROFIA");

        when(objetivoService.atualizar(eq(1), any(Objetivo.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/objetivos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("HIPERTROFIA"));
    }

    @Test
    void deletar_DeveRetornar204() throws Exception {
        doNothing().when(objetivoService).deletar(1);

        mockMvc.perform(delete("/api/v1/objetivos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void listar_QuandoVazio_DeveRetornar200() throws Exception {
        when(objetivoService.listar()).thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/api/v1/objetivos"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void atualizar_QuandoNaoExistir_DeveRetornar400() throws Exception {
        ObjetivoRequest request = new ObjetivoRequest();
        request.setTipo("HIPERTROFIA");

        when(objetivoService.atualizar(eq(99), any(Objetivo.class)))
                .thenThrow(new IllegalArgumentException("Objetivo nao encontrado: 99"));

        mockMvc.perform(put("/api/v1/objetivos/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletar_QuandoNaoExistir_DeveRetornar400() throws Exception {
        doThrow(new IllegalArgumentException("Objetivo nao encontrado: 99"))
                .when(objetivoService).deletar(99);

        mockMvc.perform(delete("/api/v1/objetivos/99"))
                .andExpect(status().isBadRequest());
    }
}
