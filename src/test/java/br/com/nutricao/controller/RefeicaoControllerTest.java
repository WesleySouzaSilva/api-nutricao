package br.com.nutricao.api.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
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

import br.com.nutricao.api.exception.EntidadeNaoEncontradaException;
import br.com.nutricao.application.dto.RefeicaoRequest;
import br.com.nutricao.application.service.RefeicaoService;
import br.com.nutricao.domain.model.Refeicao;
import br.com.nutricao.domain.model.Usuario;

@WebMvcTest(RefeicaoController.class)
@AutoConfigureMockMvc(addFilters = false)
class RefeicaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RefeicaoService refeicaoService;

    private Refeicao refeicao;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        refeicao = new Refeicao();
        refeicao.setId(1);
        refeicao.setNome("Cafe da Manha");
        refeicao.setDataRefeicao(LocalDateTime.now());
        refeicao.setUsuario(usuario);
    }

    @Test
    void criar_DeveRetornar201() throws Exception {
        RefeicaoRequest request = new RefeicaoRequest();
        request.setNome("Cafe da Manha");
        request.setUsuarioId(1);
        request.setDataRefeicao(LocalDateTime.now());

        when(refeicaoService.criar(any(Refeicao.class))).thenReturn(refeicao);

        mockMvc.perform(post("/api/v1/refeicoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Cafe da Manha"));
    }

    @Test
    void listar_SemFiltros_DeveRetornar200() throws Exception {
        when(refeicaoService.listar()).thenReturn(Arrays.asList(refeicao));

        mockMvc.perform(get("/api/v1/refeicoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void listar_ComUsuarioId_DeveRetornar200() throws Exception {
        when(refeicaoService.buscarPorUsuario(1)).thenReturn(Arrays.asList(refeicao));

        mockMvc.perform(get("/api/v1/refeicoes").param("usuarioId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void listar_ComTodosFiltros_DeveRetornar200() throws Exception {
        when(refeicaoService.buscarPorUsuarioEPeriodo(eq(1), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(refeicao));

        mockMvc.perform(get("/api/v1/refeicoes")
                .param("usuarioId", "1")
                .param("inicio", "2024-01-01T00:00:00")
                .param("fim", "2024-01-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void listar_ComUsuarioEInicioSemFim_DeveRetornar200() throws Exception {
        when(refeicaoService.buscarPorUsuario(1)).thenReturn(Arrays.asList(refeicao));

        mockMvc.perform(get("/api/v1/refeicoes")
                .param("usuarioId", "1")
                .param("inicio", "2024-01-01T00:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar200() throws Exception {
        when(refeicaoService.buscarPorId(1)).thenReturn(Optional.of(refeicao));

        mockMvc.perform(get("/api/v1/refeicoes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornar404() throws Exception {
        when(refeicaoService.buscarPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/refeicoes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void atualizar_DeveRetornar200() throws Exception {
        RefeicaoRequest request = new RefeicaoRequest();
        request.setNome("Cafe da Manha Reforcado");

        Refeicao updated = new Refeicao();
        updated.setId(1);
        updated.setNome("Cafe da Manha Reforcado");

        when(refeicaoService.atualizar(eq(1), any(Refeicao.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/refeicoes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Cafe da Manha Reforcado"));
    }

    @Test
    void deletar_DeveRetornar204() throws Exception {
        doNothing().when(refeicaoService).deletar(1);

        mockMvc.perform(delete("/api/v1/refeicoes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void listar_SemFiltros_QuandoVazio_DeveRetornar200() throws Exception {
        when(refeicaoService.listar()).thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/api/v1/refeicoes"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void listar_ComUsuarioId_QuandoVazio_DeveRetornar200() throws Exception {
        when(refeicaoService.buscarPorUsuario(99)).thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/api/v1/refeicoes").param("usuarioId", "99"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void atualizar_QuandoNaoExistir_DeveRetornar404() throws Exception {
        RefeicaoRequest request = new RefeicaoRequest();
        request.setNome("Cafe da Manha");

        when(refeicaoService.atualizar(eq(99), any(Refeicao.class)))
                .thenThrow(new EntidadeNaoEncontradaException("Refeicao nao encontrada: 99"));

        mockMvc.perform(put("/api/v1/refeicoes/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletar_QuandoNaoExistir_DeveRetornar404() throws Exception {
        doThrow(new EntidadeNaoEncontradaException("Refeicao nao encontrada: 99"))
                .when(refeicaoService).deletar(99);

        mockMvc.perform(delete("/api/v1/refeicoes/99"))
                .andExpect(status().isNotFound());
    }
}
