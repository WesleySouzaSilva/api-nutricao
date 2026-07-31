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
import br.com.nutricao.domain.dto.filtro.RegistroDiarioCamposFiltro;
import br.com.nutricao.domain.dto.insercao.RegistroDiarioRequest;
import br.com.nutricao.services.RegistroDiarioService;
import br.com.nutricao.domain.RegistroDiario;
import br.com.nutricao.domain.Usuario;

@WebMvcTest(RegistroDiarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class RegistroDiarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegistroDiarioService registroDiarioService;

    private RegistroDiario registro;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        registro = new RegistroDiario();
        registro.setId(1);
        registro.setUsuario(usuario);
        registro.setData(LocalDate.now());
        registro.setCaloriasConsumidas(new BigDecimal("2000"));
        registro.setProteinasConsumidas(new BigDecimal("100"));
        registro.setCarboidratosConsumidos(new BigDecimal("200"));
        registro.setGordurasConsumidas(new BigDecimal("50"));
    }

    @Test
    void criar_DeveRetornar201() throws Exception {
        RegistroDiarioRequest request = new RegistroDiarioRequest();
        request.setUsuarioId(1);
        request.setData(LocalDate.now());
        request.setCaloriasConsumidas(new BigDecimal("2000"));

        when(registroDiarioService.criar(any(RegistroDiario.class))).thenReturn(registro);

        mockMvc.perform(post("/api/v1/registros-diarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void listar_SemFiltros_DeveRetornar200() throws Exception {
        Page<RegistroDiario> page = new PageImpl<>(Arrays.asList(registro));
        when(registroDiarioService.listarTodosFiltro(any(RegistroDiarioCamposFiltro.class), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/registros-diarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1));
    }

    @Test
    void listar_ComUsuarioId_DeveRetornar200() throws Exception {
        Page<RegistroDiario> page = new PageImpl<>(Arrays.asList(registro));
        when(registroDiarioService.listarTodosFiltro(any(RegistroDiarioCamposFiltro.class), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/registros-diarios").param("usuarioId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1));
    }

    @Test
    void listar_ComUsuarioEPeriodo_DeveRetornar200() throws Exception {
        Page<RegistroDiario> page = new PageImpl<>(Arrays.asList(registro));
        when(registroDiarioService.listarTodosFiltro(any(RegistroDiarioCamposFiltro.class), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/registros-diarios")
                .param("usuarioId", "1")
                .param("dataInicio", "2024-01-01")
                .param("dataFim", "2024-01-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1));
    }

    @Test
    void listar_ComUsuarioEInicioSemFim_DeveRetornar200() throws Exception {
        Page<RegistroDiario> page = new PageImpl<>(Arrays.asList(registro));
        when(registroDiarioService.listarTodosFiltro(any(RegistroDiarioCamposFiltro.class), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/registros-diarios")
                .param("usuarioId", "1")
                .param("dataInicio", "2024-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1));
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar200() throws Exception {
        when(registroDiarioService.buscarPorId(1)).thenReturn(Optional.of(registro));

        mockMvc.perform(get("/api/v1/registros-diarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornar404() throws Exception {
        when(registroDiarioService.buscarPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/registros-diarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void atualizar_DeveRetornar200() throws Exception {
        RegistroDiarioRequest request = new RegistroDiarioRequest();
        request.setCaloriasConsumidas(new BigDecimal("2500"));

        RegistroDiario updated = new RegistroDiario();
        updated.setId(1);
        updated.setCaloriasConsumidas(new BigDecimal("2500"));

        when(registroDiarioService.atualizar(eq(1), any(RegistroDiario.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/registros-diarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.caloriasConsumidas").value(2500));
    }

    @Test
    void deletar_DeveRetornar204() throws Exception {
        doNothing().when(registroDiarioService).deletar(1);

        mockMvc.perform(delete("/api/v1/registros-diarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void listar_QuandoVazio_DeveRetornar200() throws Exception {
        Page<RegistroDiario> page = new PageImpl<>(java.util.Collections.emptyList());
        when(registroDiarioService.listarTodosFiltro(any(RegistroDiarioCamposFiltro.class), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/registros-diarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    void atualizar_QuandoNaoExistir_DeveRetornar404() throws Exception {
        RegistroDiarioRequest request = new RegistroDiarioRequest();
        request.setCaloriasConsumidas(new BigDecimal("2500"));

        when(registroDiarioService.atualizar(eq(99), any(RegistroDiario.class)))
                .thenThrow(new EntidadeNaoEncontradaException("RegistroDiario nao encontrado: 99"));

        mockMvc.perform(put("/api/v1/registros-diarios/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletar_QuandoNaoExistir_DeveRetornar404() throws Exception {
        doThrow(new EntidadeNaoEncontradaException("RegistroDiario nao encontrado: 99"))
                .when(registroDiarioService).deletar(99);

        mockMvc.perform(delete("/api/v1/registros-diarios/99"))
                .andExpect(status().isNotFound());
    }
}
