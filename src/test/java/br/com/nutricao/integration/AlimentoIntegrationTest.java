package br.com.nutricao.integration;

import static org.junit.jupiter.api.Assertions.*;

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
class AlimentoIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Object> categoriaRequest;
    private Map<String, Object> alimentoRequest;

    @BeforeEach
    void setUp() {
        categoriaRequest = new LinkedHashMap<>();
        categoriaRequest.put("nome", "Frutas");
    }

    private static int categoriaCounter = 0;

    private Integer criarCategoria() {
        categoriaCounter++;
        Map<String, Object> req = new LinkedHashMap<>();
        req.put("nome", "Frutas" + categoriaCounter);
        return (Integer) restTemplate.postForEntity(
                "/api/v1/categorias", req, Map.class).getBody().get("id");
    }

    @Test
    void criarEListar_DeveRetornarDadosCorretos() {
        Integer categoriaId = criarCategoria();

        alimentoRequest = new LinkedHashMap<>();
        alimentoRequest.put("nome", "Banana");
        alimentoRequest.put("kcal", "89");
        alimentoRequest.put("proteina", "1.1");
        alimentoRequest.put("gordura", "0.3");
        alimentoRequest.put("carboidrato", "23");
        alimentoRequest.put("categoriaAlimentoId", categoriaId);

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/alimentos", alimentoRequest, Map.class);

        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody().get("id"));
        assertEquals("Banana", postResponse.getBody().get("nome"));

        ResponseEntity<Map[]> listaResponse = restTemplate.getForEntity(
                "/api/v1/alimentos", Map[].class);

        assertEquals(HttpStatus.OK, listaResponse.getStatusCode());
        assertTrue(listaResponse.getBody().length > 0);
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornarAlimento() {
        Integer categoriaId = criarCategoria();

        alimentoRequest = new LinkedHashMap<>();
        alimentoRequest.put("nome", "Maca");
        alimentoRequest.put("kcal", "52");
        alimentoRequest.put("categoriaAlimentoId", categoriaId);

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/alimentos", alimentoRequest, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        ResponseEntity<Map> getResponse = restTemplate.getForEntity(
                "/api/v1/alimentos/" + id, Map.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("Maca", getResponse.getBody().get("nome"));
    }

    @Test
    void filtrarPorNome_DeveRetornarResultadosFiltrados() {
        Integer categoriaId = criarCategoria();

        alimentoRequest = new LinkedHashMap<>();
        alimentoRequest.put("nome", "Banana");
        alimentoRequest.put("kcal", "89");
        alimentoRequest.put("categoriaAlimentoId", categoriaId);

        restTemplate.postForEntity("/api/v1/alimentos", alimentoRequest, Map.class);

        ResponseEntity<Map[]> response = restTemplate.getForEntity(
                "/api/v1/alimentos?nome=Bana", Map[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void atualizar_DeveRetornarDadosAtualizados() {
        Integer categoriaId = criarCategoria();

        alimentoRequest = new LinkedHashMap<>();
        alimentoRequest.put("nome", "Banana");
        alimentoRequest.put("categoriaAlimentoId", categoriaId);

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/alimentos", alimentoRequest, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        Map<String, Object> updateRequest = new LinkedHashMap<>();
        updateRequest.put("nome", "Banana Prata");
        updateRequest.put("categoriaAlimentoId", categoriaId);

        HttpEntity<Map> requestEntity = new HttpEntity<>(updateRequest);
        ResponseEntity<Map> putResponse = restTemplate.exchange(
                "/api/v1/alimentos/" + id, HttpMethod.PUT, requestEntity, Map.class);

        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        assertEquals("Banana Prata", putResponse.getBody().get("nome"));
    }

    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornar404() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/api/v1/alimentos/99999", Map.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deletar_DeveRetornar204() {
        Integer categoriaId = criarCategoria();

        alimentoRequest = new LinkedHashMap<>();
        alimentoRequest.put("nome", "Banana");
        alimentoRequest.put("categoriaAlimentoId", categoriaId);

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/alimentos", alimentoRequest, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/v1/alimentos/" + id, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }
}
