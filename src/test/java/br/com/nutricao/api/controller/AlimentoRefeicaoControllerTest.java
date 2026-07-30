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
}
