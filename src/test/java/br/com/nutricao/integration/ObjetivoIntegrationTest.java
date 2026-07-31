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
class ObjetivoIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Object> usuarioRequest;

    @BeforeEach
    void setUp() {
        usuarioRequest = new LinkedHashMap<>();
        usuarioRequest.put("nome", "Usuario Obj");
        usuarioRequest.put("email", "usuario.obj@email.com");
        usuarioRequest.put("senha", "senha123");
        usuarioRequest.put("sexo", "MASCULINO");
        usuarioRequest.put("medida", "KG");
        usuarioRequest.put("tipoLogin", "EMAIL");
    }

    private static int usuarioCounter = 0;

    private Integer criarUsuario() {
        usuarioCounter++;
        Map<String, Object> req = new LinkedHashMap<>(usuarioRequest);
        req.put("email", "usuario.obj" + usuarioCounter + "@email.com");
        return (Integer) restTemplate.postForEntity(
                "/api/v1/usuarios", req, Map.class).getBody().get("id");
    }

    @Test
    void criarObjetivo_DeveRetornar201() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("tipo", "EMAGRECER");
        request.put("dataInicio", LocalDate.now().toString());

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/v1/objetivos", request, Map.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().get("id"));
    }

    @Test
    void buscarPorUsuario_DeveRetornarObjetivos() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("tipo", "HIPERTROFIA");
        request.put("dataInicio", LocalDate.now().toString());

        restTemplate.postForEntity("/api/v1/objetivos", request, Map.class);

        ResponseEntity<Map[]> response = restTemplate.getForEntity(
                "/api/v1/objetivos/usuario/" + usuarioId, Map[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornar404() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/api/v1/objetivos/99999", Map.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void atualizar_DeveRetornarDadosAtualizados() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("tipo", "EMAGRECER");
        request.put("dataInicio", LocalDate.now().toString());

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/objetivos", request, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        Map<String, Object> updateRequest = new LinkedHashMap<>();
        updateRequest.put("tipo", "HIPERTROFIA");

        HttpEntity<Map> requestEntity = new HttpEntity<>(updateRequest);
        ResponseEntity<Map> putResponse = restTemplate.exchange(
                "/api/v1/objetivos/" + id, HttpMethod.PUT, requestEntity, Map.class);

        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        assertEquals("HIPERTROFIA", putResponse.getBody().get("tipo"));
    }

    @Test
    void deletar_DeveRetornar204() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("tipo", "EMAGRECER");
        request.put("dataInicio", LocalDate.now().toString());

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/objetivos", request, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/v1/objetivos/" + id, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }
}
