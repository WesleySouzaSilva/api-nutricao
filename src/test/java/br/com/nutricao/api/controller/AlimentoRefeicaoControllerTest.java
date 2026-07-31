package br.com.nutricao.api.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.nutricao.application.dto.AlimentoRefeicaoRequest;
import br.com.nutricao.application.service.AlimentoRefeicaoService;
import br.com.nutricao.domain.model.Alimento;
import br.com.nutricao.domain.model.AlimentoRefeicao;
import br.com.nutricao.domain.model.Refeicao;

@WebMvcTest(AlimentoRefeicaoController.class)
@AutoConfigureMockMvc(addFilters = false)
class AlimentoRefeicaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AlimentoRefeicaoService alimentoRefeicaoService;

    private AlimentoRefeicao alimentoRefeicao;

    @BeforeEach
    void setUp() {
        Refeicao refeicao = new Refeicao();
        refeicao.setId(1);

        Alimento alimento = new Alimento();
        alimento.setId(1);

        alimentoRefeicao = new AlimentoRefeicao();
        alimentoRefeicao.setId(1);
        alimentoRefeicao.setRefeicao(refeicao);
        alimentoRefeicao.setAlimento(alimento);
        alimentoRefeicao.setQuantidade(new BigDecimal("100"));
        alimentoRefeicao.setPorcao("g");
    }

    @Test
    void criar_DeveRetornar201() throws Exception {
        AlimentoRefeicaoRequest request = new AlimentoRefeicaoRequest();
        request.setRefeicaoId(1);
        request.setAlimentoId(1);
        request.setQuantidade(new BigDecimal("100"));
        request.setPorcao("g");

        when(alimentoRefeicaoService.criar(any(AlimentoRefeicao.class))).thenReturn(alimentoRefeicao);

        mockMvc.perform(post("/api/v1/alimentos-refeicao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void criar_ComIdsNulos_DeveRetornar201() throws Exception {
        AlimentoRefeicaoRequest request = new AlimentoRefeicaoRequest();
        request.setQuantidade(new BigDecimal("200"));
        request.setPorcao("ml");

        AlimentoRefeicao saved = new AlimentoRefeicao();
        saved.setId(2);
        saved.setQuantidade(new BigDecimal("200"));
        saved.setPorcao("ml");

        when(alimentoRefeicaoService.criar(any(AlimentoRefeicao.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/alimentos-refeicao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void buscarPorRefeicao_ComDadosNulos_DeveRetornar200() throws Exception {
        AlimentoRefeicao semVinculo = new AlimentoRefeicao();
        semVinculo.setId(2);
        semVinculo.setQuantidade(new BigDecimal("300"));
        semVinculo.setPorcao("g");

        when(alimentoRefeicaoService.buscarPorRefeicao(2)).thenReturn(Arrays.asList(semVinculo));

        mockMvc.perform(get("/api/v1/alimentos-refeicao/refeicao/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2));
    }

    @Test
    void buscarPorRefeicao_DeveRetornar200() throws Exception {
        when(alimentoRefeicaoService.buscarPorRefeicao(1)).thenReturn(Arrays.asList(alimentoRefeicao));

        mockMvc.perform(get("/api/v1/alimentos-refeicao/refeicao/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void deletar_DeveRetornar204() throws Exception {
        doNothing().when(alimentoRefeicaoService).deletar(1);

        mockMvc.perform(delete("/api/v1/alimentos-refeicao/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletarPorRefeicao_DeveRetornar204() throws Exception {
        doNothing().when(alimentoRefeicaoService).deletarPorRefeicao(1);

        mockMvc.perform(delete("/api/v1/alimentos-refeicao/refeicao/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletar_QuandoNaoExistir_DeveRetornar400() throws Exception {
        doThrow(new IllegalArgumentException("Vinculo AlimentoRefeicao nao encontrado: 99"))
                .when(alimentoRefeicaoService).deletar(99);

        mockMvc.perform(delete("/api/v1/alimentos-refeicao/99"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buscarPorRefeicao_QuandoVazio_DeveRetornar200() throws Exception {
        when(alimentoRefeicaoService.buscarPorRefeicao(99)).thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/api/v1/alimentos-refeicao/refeicao/99"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
