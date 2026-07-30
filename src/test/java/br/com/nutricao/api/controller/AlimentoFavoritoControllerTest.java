package br.com.nutricao.api.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
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

import br.com.nutricao.application.dto.AlimentoFavoritoRequest;
import br.com.nutricao.application.service.AlimentoFavoritoService;
import br.com.nutricao.domain.model.Alimento;
import br.com.nutricao.domain.model.AlimentoFavorito;
import br.com.nutricao.domain.model.Usuario;

@WebMvcTest(AlimentoFavoritoController.class)
@AutoConfigureMockMvc(addFilters = false)
class AlimentoFavoritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AlimentoFavoritoService alimentoFavoritoService;

    private AlimentoFavorito favorito;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        Alimento alimento = new Alimento();
        alimento.setId(1);

        favorito = new AlimentoFavorito();
        favorito.setId(1);
        favorito.setUsuario(usuario);
        favorito.setAlimento(alimento);
        favorito.setDataAdicao(LocalDateTime.now());
    }

    @Test
    void adicionar_DeveRetornar201() throws Exception {
        AlimentoFavoritoRequest request = new AlimentoFavoritoRequest();
        request.setUsuarioId(1);
        request.setAlimentoId(1);

        when(alimentoFavoritoService.adicionar(any(AlimentoFavorito.class))).thenReturn(favorito);

        mockMvc.perform(post("/api/v1/favoritos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorUsuario_DeveRetornar200() throws Exception {
        when(alimentoFavoritoService.buscarPorUsuario(1)).thenReturn(Arrays.asList(favorito));

        mockMvc.perform(get("/api/v1/favoritos/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void remover_DeveRetornar204() throws Exception {
        doNothing().when(alimentoFavoritoService).remover(1, 1);

        mockMvc.perform(delete("/api/v1/favoritos/usuario/1/alimento/1"))
                .andExpect(status().isNoContent());
    }
}
