package br.com.nutricao.api.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(IntegrationTestConfig.class)
class UsuarioIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Object> usuarioRequest;
    private static int counter = 0;

    @BeforeEach
    void setUp() {
        counter++;
        usuarioRequest = new LinkedHashMap<>();
        usuarioRequest.put("nome", "Maria Souza");
        usuarioRequest.put("email", "maria" + counter + "@email.com");
        usuarioRequest.put("senha", "senha123");
        usuarioRequest.put("sexo", "FEMININO");
        usuarioRequest.put("medida", "KG");
        usuarioRequest.put("tipoLogin", "EMAIL");
    }

    @Test
    void criarEListarUsuarios_DeveRetornarDadosCorretos() {
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/v1/usuarios", usuarioRequest, Map.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().get("id"));
        assertEquals("Maria Souza", response.getBody().get("nome"));
        assertEquals(usuarioRequest.get("email"), response.getBody().get("email"));

        ResponseEntity<String> listaResponse = restTemplate.getForEntity(
                "/api/v1/usuarios", String.class);

        assertEquals(HttpStatus.OK, listaResponse.getStatusCode());
    }

    @Test
    void criarEBuscarPorId_DeveRetornarUsuario() {
        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/usuarios", usuarioRequest, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        ResponseEntity<Map> getResponse = restTemplate.getForEntity(
                "/api/v1/usuarios/" + id, Map.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("Maria Souza", getResponse.getBody().get("nome"));
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornar404() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/api/v1/usuarios/99999", Map.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void buscarPorEmail_DeveRetornarUsuario() {
        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/usuarios", usuarioRequest, Map.class);
        String email = (String) postResponse.getBody().get("email");

        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/api/v1/usuarios/email/" + email, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Maria Souza", response.getBody().get("nome"));
    }

    @Test
    void atualizarUsuario_DeveRetornarDadosAtualizados() {
        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/usuarios", usuarioRequest, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        Map<String, Object> updateRequest = new LinkedHashMap<>(usuarioRequest);
        updateRequest.put("nome", "Maria Souza Atualizada");

        HttpEntity<Map> requestEntity = new HttpEntity<>(updateRequest);
        ResponseEntity<Map> putResponse = restTemplate.exchange(
                "/api/v1/usuarios/" + id, HttpMethod.PUT, requestEntity, Map.class);

        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        assertEquals("Maria Souza Atualizada", putResponse.getBody().get("nome"));
    }

    @Test
    void deletarUsuario_DeveRetornar204() {
        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/usuarios", usuarioRequest, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/v1/usuarios/" + id, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        ResponseEntity<Map> getResponse = restTemplate.getForEntity(
                "/api/v1/usuarios/" + id, Map.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}
