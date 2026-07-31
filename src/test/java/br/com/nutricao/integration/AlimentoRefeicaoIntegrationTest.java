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
class AlimentoRefeicaoIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Object> usuarioRequest;
    private Map<String, Object> categoriaRequest;
    private Map<String, Object> alimentoRequest;
    private Map<String, Object> refeicaoRequest;

    @BeforeEach
    void setUp() {
        usuarioRequest = new LinkedHashMap<>();
        usuarioRequest.put("nome", "Usuario AR");
        usuarioRequest.put("email", "usuario.ar@email.com");
        usuarioRequest.put("senha", "senha123");
        usuarioRequest.put("sexo", "MASCULINO");
        usuarioRequest.put("medida", "KG");
        usuarioRequest.put("tipoLogin", "EMAIL");

        categoriaRequest = new LinkedHashMap<>();
        categoriaRequest.put("nome", "Proteinas");
    }

    private static int usuarioCounter = 0;
    private static int categoriaCounter = 0;

    private Integer criarUsuario() {
        usuarioCounter++;
        Map<String, Object> req = new LinkedHashMap<>(usuarioRequest);
        req.put("email", "usuario.ar" + usuarioCounter + "@email.com");
        return (Integer) restTemplate.postForEntity(
                "/api/v1/usuarios", req, Map.class).getBody().get("id");
    }

    private Integer criarCategoria() {
        categoriaCounter++;
        Map<String, Object> req = new LinkedHashMap<>(categoriaRequest);
        req.put("nome", "Proteinas" + categoriaCounter);
        return (Integer) restTemplate.postForEntity(
                "/api/v1/categorias", req, Map.class).getBody().get("id");
    }

    private Integer criarAlimento() {
        Map<String, Object> req = new LinkedHashMap<>();
        req.put("nome", "Frango");
        req.put("kcal", "165");
        req.put("categoriaAlimentoId", criarCategoria());
        return (Integer) restTemplate.postForEntity(
                "/api/v1/alimentos", req, Map.class).getBody().get("id");
    }

    private Integer criarRefeicao() {
        Map<String, Object> req = new LinkedHashMap<>();
        req.put("nome", "Almoco");
        req.put("dataRefeicao", "2024-06-15T12:00:00");
        req.put("usuarioId", criarUsuario());
        return (Integer) restTemplate.postForEntity(
                "/api/v1/refeicoes", req, Map.class).getBody().get("id");
    }

    @Test
    void adicionarAlimentoARefeicao_DeveRetornar201() {
        Integer refeicaoId = criarRefeicao();
        Integer alimentoId = criarAlimento();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("refeicaoId", refeicaoId);
        request.put("alimentoId", alimentoId);
        request.put("quantidade", 200);
        request.put("porcao", "g");

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/v1/alimentos-refeicao", request, Map.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().get("id"));
    }

    @Test
    void buscarPorRefeicao_DeveRetornarAlimentos() {
        Integer refeicaoId = criarRefeicao();
        Integer alimentoId = criarAlimento();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("refeicaoId", refeicaoId);
        request.put("alimentoId", alimentoId);
        request.put("quantidade", 100);

        restTemplate.postForEntity("/api/v1/alimentos-refeicao", request, Map.class);

        ResponseEntity<Map[]> response = restTemplate.getForEntity(
                "/api/v1/alimentos-refeicao/refeicao/" + refeicaoId, Map[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void deletar_DeveRetornar204() {
        Integer refeicaoId = criarRefeicao();
        Integer alimentoId = criarAlimento();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("refeicaoId", refeicaoId);
        request.put("alimentoId", alimentoId);
        request.put("quantidade", 100);

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/alimentos-refeicao", request, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/v1/alimentos-refeicao/" + id, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }

    @Test
    void deletarPorRefeicao_DeveRetornar204() {
        Integer refeicaoId = criarRefeicao();
        Integer alimentoId = criarAlimento();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("refeicaoId", refeicaoId);
        request.put("alimentoId", alimentoId);
        request.put("quantidade", 100);

        restTemplate.postForEntity("/api/v1/alimentos-refeicao", request, Map.class);

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/v1/alimentos-refeicao/refeicao/" + refeicaoId,
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }
}
