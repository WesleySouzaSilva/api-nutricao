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
class RegistroDiarioIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Object> usuarioRequest;

    @BeforeEach
    void setUp() {
        usuarioRequest = new LinkedHashMap<>();
        usuarioRequest.put("nome", "Usuario RD");
        usuarioRequest.put("email", "usuario.rd@email.com");
        usuarioRequest.put("senha", "senha123");
        usuarioRequest.put("sexo", "MASCULINO");
        usuarioRequest.put("medida", "KG");
        usuarioRequest.put("tipoLogin", "EMAIL");
    }

    private static int usuarioCounter = 0;

    private Integer criarUsuario() {
        usuarioCounter++;
        Map<String, Object> req = new LinkedHashMap<>(usuarioRequest);
        req.put("email", "usuario.rd" + usuarioCounter + "@email.com");
        return (Integer) restTemplate.postForEntity(
                "/api/v1/usuarios", req, Map.class).getBody().get("id");
    }

    @Test
    void criarRegistro_DeveRetornar201() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("data", LocalDate.now().toString());
        request.put("caloriasConsumidas", 2000);
        request.put("proteinasConsumidas", 100);
        request.put("carboidratosConsumidos", 200);
        request.put("gordurasConsumidas", 50);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/v1/registros-diarios", request, Map.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().get("id"));
    }

    @Test
    void listarRegistros_DeveRetornarLista() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("data", LocalDate.now().toString());
        request.put("caloriasConsumidas", 2000);

        restTemplate.postForEntity("/api/v1/registros-diarios", request, Map.class);

        ResponseEntity<Map[]> response = restTemplate.getForEntity(
                "/api/v1/registros-diarios", Map[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void filtrarPorUsuarioEPeriodo_DeveRetornarRegistros() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("data", "2024-06-15");
        request.put("caloriasConsumidas", 2000);

        restTemplate.postForEntity("/api/v1/registros-diarios", request, Map.class);

        ResponseEntity<Map[]> response = restTemplate.getForEntity(
                "/api/v1/registros-diarios?usuarioId=" + usuarioId
                + "&inicio=2024-01-01&fim=2024-12-31", Map[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornar404() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/api/v1/registros-diarios/99999", Map.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void atualizar_DeveRetornarDadosAtualizados() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("data", LocalDate.now().toString());
        request.put("caloriasConsumidas", 2000);

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/registros-diarios", request, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        Map<String, Object> updateRequest = new LinkedHashMap<>();
        updateRequest.put("caloriasConsumidas", 2500);

        HttpEntity<Map> requestEntity = new HttpEntity<>(updateRequest);
        ResponseEntity<Map> putResponse = restTemplate.exchange(
                "/api/v1/registros-diarios/" + id, HttpMethod.PUT, requestEntity, Map.class);

        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        assertEquals(2500, ((Number) putResponse.getBody().get("caloriasConsumidas")).intValue());
    }

    @Test
    void deletar_DeveRetornar204() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("data", LocalDate.now().toString());
        request.put("caloriasConsumidas", 2000);

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/registros-diarios", request, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/v1/registros-diarios/" + id, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }
}
