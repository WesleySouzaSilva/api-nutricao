package br.com.nutricao.api.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import br.com.nutricao.application.dto.UsuarioRequest;
import br.com.nutricao.application.service.UsuarioService;
import br.com.nutricao.domain.model.Usuario;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Joao");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("123");
        usuario.setSexo("MASCULINO");
        usuario.setMedida("KG");
        usuario.setTipoLogin("EMAIL");
        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setDataNascimento(LocalDate.of(1990, 1, 1));
        usuario.setAltura(new BigDecimal("1.75"));
    }

    @Test
    void criar_DeveRetornar201() throws Exception {
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Joao");
        request.setEmail("joao@email.com");
        request.setSenha("123");
        request.setSexo("MASCULINO");
        request.setMedida("KG");
        request.setTipoLogin("EMAIL");

        when(usuarioService.criar(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Joao"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    void listar_DeveRetornar200() throws Exception {
        when(usuarioService.listar()).thenReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Joao"));
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornar200() throws Exception {
        when(usuarioService.buscarPorId(1)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornar404() throws Exception {
        when(usuarioService.buscarPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorEmail_DeveRetornar200() throws Exception {
        when(usuarioService.buscarPorEmail("joao@email.com")).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/v1/usuarios/email/joao@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    void atualizar_DeveRetornar200() throws Exception {
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Joao Atualizado");
        request.setEmail("joao@email.com");
        request.setSenha("123");
        request.setSexo("MASCULINO");
        request.setMedida("KG");
        request.setTipoLogin("EMAIL");

        Usuario updated = new Usuario();
        updated.setId(1);
        updated.setNome("Joao Atualizado");
        updated.setEmail("joao@email.com");
        updated.setDataCadastro(LocalDateTime.now());

        when(usuarioService.atualizar(eq(1), any(Usuario.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Joao Atualizado"));
    }

    @Test
    void deletar_DeveRetornar204() throws Exception {
        doNothing().when(usuarioService).deletar(1);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
