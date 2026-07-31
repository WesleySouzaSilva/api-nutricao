package br.com.nutricao.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.nutricao.domain.dto.visualizacao.Login;
import br.com.nutricao.domain.dto.visualizacao.LoginToken;
import br.com.nutricao.security.JWTUtil;
import br.com.nutricao.security.UsuarioDetailsService;
import br.com.nutricao.security.UsuarioDetails;
import br.com.nutricao.domain.Usuario;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private UsuarioDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Usuario Teste");
        usuario.setEmail("user@email.com");
        usuario.setSenha("senha123");
        usuario.setSexo("M");
        return usuario;
    }

    @Test
    void login_ComCredenciaisValidas_DeveRetornarToken() throws Exception {
        Login request = new Login("user@email.com", "senha123");
        Usuario usuario = criarUsuario();
        UsuarioDetails userDetails = new UsuarioDetails(usuario);

        when(userDetailsService.loadUserByUsername("user@email.com")).thenReturn(userDetails);
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtUtil.generateToken("user@email.com")).thenReturn("token-aqui");

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-aqui"))
                .andExpect(jsonPath("$.tipo").value("Bearer"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("user@email.com"))
                .andExpect(jsonPath("$.nome").value("Usuario Teste"));
    }

    @Test
    void login_ComEmailInexistente_DeveRetornar400() throws Exception {
        Login request = new Login("inexistente@email.com", "senha123");

        when(userDetailsService.loadUserByUsername("inexistente@email.com"))
                .thenThrow(new UsernameNotFoundException("Usuario nao encontrado"));

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value(org.hamcrest.Matchers.containsString("E-mail nao encontrado")));
    }

    @Test
    void login_ComSenhaInvalida_DeveRetornar400() throws Exception {
        Login request = new Login("user@email.com", "senha-errada");
        Usuario usuario = criarUsuario();
        UsuarioDetails userDetails = new UsuarioDetails(usuario);

        when(userDetailsService.loadUserByUsername("user@email.com")).thenReturn(userDetails);
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Senha incorreta"));

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value(org.hamcrest.Matchers.containsString("Senha incorreta")));
    }

    @Test
    void loginToken_ComTokenValido_DeveRetornarToken() throws Exception {
        LoginToken loginToken = new LoginToken("token-id-valido");
        Usuario usuario = criarUsuario();
        UsuarioDetails userDetails = new UsuarioDetails(usuario);

        when(userDetailsService.loadUserByTokenId("token-id-valido")).thenReturn(userDetails);
        when(jwtUtil.generateToken("user@email.com")).thenReturn("token-aqui");

        mockMvc.perform(post("/api/v1/auth/login/token_id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-aqui"))
                .andExpect(jsonPath("$.tipo").value("Bearer"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("user@email.com"));
    }

    @Test
    void loginToken_ComTokenInvalido_DeveRetornar400() throws Exception {
        LoginToken loginToken = new LoginToken("token-invalido");

        when(userDetailsService.loadUserByTokenId("token-invalido"))
                .thenThrow(new UsernameNotFoundException("Token invalido"));

        mockMvc.perform(post("/api/v1/auth/login/token_id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginToken)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value(org.hamcrest.Matchers.containsString("Token invalido")));
    }

    @Test
    void loginToken_ComTokenVazio_DeveRetornar400() throws Exception {
        LoginToken loginToken = new LoginToken("");

        mockMvc.perform(post("/api/v1/auth/login/token_id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginToken)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value(org.hamcrest.Matchers.containsString("Token ID nao informado")));
    }

    @Test
    void loginToken_ComTokenNulo_DeveRetornar400() throws Exception {
        LoginToken loginToken = new LoginToken();

        mockMvc.perform(post("/api/v1/auth/login/token_id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginToken)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value(org.hamcrest.Matchers.containsString("Token ID nao informado")));
    }
}
