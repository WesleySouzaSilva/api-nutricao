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
class MetaNutricionalIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Object> usuarioRequest;

    @BeforeEach
    void setUp() {
        usuarioRequest = new LinkedHashMap<>();
        usuarioRequest.put("nome", "Usuario MN");
        usuarioRequest.put("email", "usuario.mn@email.com");
        usuarioRequest.put("senha", "senha123");
        usuarioRequest.put("sexo", "MASCULINO");
        usuarioRequest.put("medida", "KG");
        usuarioRequest.put("tipoLogin", "EMAIL");
    }

    private static int usuarioCounter = 0;

    private Integer criarUsuario() {
        usuarioCounter++;
        Map<String, Object> req = new LinkedHashMap<>(usuarioRequest);
        req.put("email", "usuario.mn" + usuarioCounter + "@email.com");
        return (Integer) restTemplate.postForEntity(
                "/api/v1/usuarios", req, Map.class).getBody().get("id");
    }

    @Test
    void criarMeta_DeveRetornar201() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("calorias", 2000);
        request.put("proteinas", 100);
        request.put("carboidratos", 200);
        request.put("gorduras", 50);
        request.put("dataInicio", LocalDate.now().toString());

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/v1/metas-nutricionais", request, Map.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().get("id"));
    }

    @Test
    void buscarUltimaPorUsuario_DeveRetornarMeta() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("calorias", 2000);
        request.put("dataInicio", LocalDate.now().toString());

        restTemplate.postForEntity("/api/v1/metas-nutricionais", request, Map.class);

        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/api/v1/metas-nutricionais/usuario/" + usuarioId + "/ultima", Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().get("id"));
    }

    @Test
    void buscarUltimaPorUsuario_QuandoNaoExistir_DeveRetornar404() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/api/v1/metas-nutricionais/usuario/99999/ultima", Map.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void buscarPorUsuario_DeveRetornarMetas() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("calorias", 2000);
        request.put("dataInicio", LocalDate.now().toString());

        restTemplate.postForEntity("/api/v1/metas-nutricionais", request, Map.class);

        ResponseEntity<Map[]> response = restTemplate.getForEntity(
                "/api/v1/metas-nutricionais/usuario/" + usuarioId, Map[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void atualizar_DeveRetornarDadosAtualizados() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("calorias", 2000);
        request.put("dataInicio", LocalDate.now().toString());

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/metas-nutricionais", request, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        Map<String, Object> updateRequest = new LinkedHashMap<>();
        updateRequest.put("calorias", 2500);

        HttpEntity<Map> requestEntity = new HttpEntity<>(updateRequest);
        ResponseEntity<Map> putResponse = restTemplate.exchange(
                "/api/v1/metas-nutricionais/" + id, HttpMethod.PUT, requestEntity, Map.class);

        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
    }

    @Test
    void deletar_DeveRetornar204() {
        Integer usuarioId = criarUsuario();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("calorias", 2000);
        request.put("dataInicio", LocalDate.now().toString());

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/metas-nutricionais", request, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/v1/metas-nutricionais/" + id, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }
}
