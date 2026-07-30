package br.com.nutricao.api.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

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
class CategoriaAlimentoIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Object> categoriaRequest;

    @BeforeEach
    void setUp() {
        categoriaRequest = new LinkedHashMap<>();
        categoriaRequest.put("nome", "Frutas" + UUID.randomUUID().toString().substring(0, 8));
    }

    @Test
    void criarEListar_DeveRetornarDadosCorretos() {
        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/categorias", categoriaRequest, Map.class);

        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody().get("id"));
        assertEquals(categoriaRequest.get("nome"), postResponse.getBody().get("nome"));

        ResponseEntity<Map[]> listaResponse = restTemplate.getForEntity(
                "/api/v1/categorias", Map[].class);

        assertEquals(HttpStatus.OK, listaResponse.getStatusCode());
        assertTrue(listaResponse.getBody().length > 0);
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornarCategoria() {
        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/categorias", categoriaRequest, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        ResponseEntity<Map> getResponse = restTemplate.getForEntity(
                "/api/v1/categorias/" + id, Map.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(categoriaRequest.get("nome"), getResponse.getBody().get("nome"));
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornar404() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/api/v1/categorias/99999", Map.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void atualizar_DeveRetornarDadosAtualizados() {
        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/categorias", categoriaRequest, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        Map<String, Object> updateRequest = new LinkedHashMap<>();
        updateRequest.put("nome", "Frutas Vermelhas");

        HttpEntity<Map> requestEntity = new HttpEntity<>(updateRequest);
        ResponseEntity<Map> putResponse = restTemplate.exchange(
                "/api/v1/categorias/" + id, HttpMethod.PUT, requestEntity, Map.class);

        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        assertEquals("Frutas Vermelhas", putResponse.getBody().get("nome"));
    }

    @Test
    void deletar_DeveRetornar204() {
        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/categorias", categoriaRequest, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/v1/categorias/" + id, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }
}
