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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(IntegrationTestConfig.class)
class AlimentoFavoritoIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Object> usuarioRequest;
    private Map<String, Object> categoriaRequest;

    @BeforeEach
    void setUp() {
        usuarioRequest = new LinkedHashMap<>();
        usuarioRequest.put("nome", "Usuario Fav");
        usuarioRequest.put("email", "usuario.fav@email.com");
        usuarioRequest.put("senha", "senha123");
        usuarioRequest.put("sexo", "MASCULINO");
        usuarioRequest.put("medida", "KG");
        usuarioRequest.put("tipoLogin", "EMAIL");

        categoriaRequest = new LinkedHashMap<>();
        categoriaRequest.put("nome", "Legumes");
    }

    private static int usuarioCounter = 0;
    private static int categoriaCounter = 0;

    private Integer criarUsuario() {
        usuarioCounter++;
        Map<String, Object> req = new LinkedHashMap<>(usuarioRequest);
        req.put("email", "usuario.fav" + usuarioCounter + "@email.com");
        return (Integer) restTemplate.postForEntity(
                "/api/v1/usuarios", req, Map.class).getBody().get("id");
    }

    private Integer criarCategoria() {
        categoriaCounter++;
        Map<String, Object> req = new LinkedHashMap<>(categoriaRequest);
        req.put("nome", "Legumes" + categoriaCounter);
        return (Integer) restTemplate.postForEntity(
                "/api/v1/categorias", req, Map.class).getBody().get("id");
    }

    private Integer criarAlimento() {
        Map<String, Object> req = new LinkedHashMap<>();
        req.put("nome", "Brocolis");
        req.put("kcal", "34");
        req.put("categoriaAlimentoId", criarCategoria());
        return (Integer) restTemplate.postForEntity(
                "/api/v1/alimentos", req, Map.class).getBody().get("id");
    }

    @Test
    void adicionarFavorito_DeveRetornar201() {
        Integer usuarioId = criarUsuario();
        Integer alimentoId = criarAlimento();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("alimentoId", alimentoId);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/v1/favoritos", request, Map.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().get("id"));
    }

    @Test
    void buscarPorUsuario_DeveRetornarFavoritos() {
        Integer usuarioId = criarUsuario();
        Integer alimentoId = criarAlimento();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("alimentoId", alimentoId);

        restTemplate.postForEntity("/api/v1/favoritos", request, Map.class);

        ResponseEntity<Map[]> response = restTemplate.getForEntity(
                "/api/v1/favoritos/usuario/" + usuarioId, Map[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void removerFavorito_DeveRetornar204() {
        Integer usuarioId = criarUsuario();
        Integer alimentoId = criarAlimento();

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("usuarioId", usuarioId);
        request.put("alimentoId", alimentoId);

        restTemplate.postForEntity("/api/v1/favoritos", request, Map.class);

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/v1/favoritos/usuario/" + usuarioId + "/alimento/" + alimentoId,
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }
}
