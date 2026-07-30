package br.com.nutricao.api.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
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
class RefeicaoIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Object> usuarioRequest;
    private Map<String, Object> refeicaoRequest;

    @BeforeEach
    void setUp() {
        usuarioRequest = new LinkedHashMap<>();
        usuarioRequest.put("nome", "Joao Refeicao");
        usuarioRequest.put("email", "joao.refeicao@email.com");
        usuarioRequest.put("senha", "senha123");
        usuarioRequest.put("sexo", "MASCULINO");
        usuarioRequest.put("medida", "KG");
        usuarioRequest.put("tipoLogin", "EMAIL");
    }

    private static int usuarioCounter = 0;

    private Integer criarUsuario() {
        usuarioCounter++;
        Map<String, Object> req = new LinkedHashMap<>(usuarioRequest);
        req.put("email", "joao.refeicao" + usuarioCounter + "@email.com");
        return (Integer) restTemplate.postForEntity(
                "/api/v1/usuarios", req, Map.class).getBody().get("id");
    }

    @Test
    void criarEListar_DeveRetornarDadosCorretos() {
        Integer usuarioId = criarUsuario();

        refeicaoRequest = new LinkedHashMap<>();
        refeicaoRequest.put("nome", "Cafe da Manha");
        refeicaoRequest.put("dataRefeicao", LocalDateTime.now().toString());
        refeicaoRequest.put("usuarioId", usuarioId);

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/refeicoes", refeicaoRequest, Map.class);

        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody().get("id"));
        assertEquals("Cafe da Manha", postResponse.getBody().get("nome"));

        ResponseEntity<Map[]> listaResponse = restTemplate.getForEntity(
                "/api/v1/refeicoes", Map[].class);

        assertEquals(HttpStatus.OK, listaResponse.getStatusCode());
        assertTrue(listaResponse.getBody().length > 0);
    }

    @Test
    void listarPorUsuario_DeveRetornarRefeicoesDoUsuario() {
        Integer usuarioId = criarUsuario();

        refeicaoRequest = new LinkedHashMap<>();
        refeicaoRequest.put("nome", "Almoco");
        refeicaoRequest.put("dataRefeicao", "2024-06-15T12:00:00");
        refeicaoRequest.put("usuarioId", usuarioId);

        restTemplate.postForEntity("/api/v1/refeicoes", refeicaoRequest, Map.class);

        ResponseEntity<Map[]> response = restTemplate.getForEntity(
                "/api/v1/refeicoes?usuarioId=" + usuarioId, Map[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornarRefeicao() {
        Integer usuarioId = criarUsuario();

        refeicaoRequest = new LinkedHashMap<>();
        refeicaoRequest.put("nome", "Jantar");
        refeicaoRequest.put("dataRefeicao", "2024-06-15T19:00:00");
        refeicaoRequest.put("usuarioId", usuarioId);

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/refeicoes", refeicaoRequest, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        ResponseEntity<Map> getResponse = restTemplate.getForEntity(
                "/api/v1/refeicoes/" + id, Map.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("Jantar", getResponse.getBody().get("nome"));
    }

    @Test
    void atualizar_DeveRetornarDadosAtualizados() {
        Integer usuarioId = criarUsuario();

        refeicaoRequest = new LinkedHashMap<>();
        refeicaoRequest.put("nome", "Lanche");
        refeicaoRequest.put("dataRefeicao", "2024-06-15T16:00:00");
        refeicaoRequest.put("usuarioId", usuarioId);

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/refeicoes", refeicaoRequest, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        Map<String, Object> updateRequest = new LinkedHashMap<>();
        updateRequest.put("nome", "Lanche da Tarde");
        updateRequest.put("usuarioId", usuarioId);

        HttpEntity<Map> requestEntity = new HttpEntity<>(updateRequest);
        ResponseEntity<Map> putResponse = restTemplate.exchange(
                "/api/v1/refeicoes/" + id, HttpMethod.PUT, requestEntity, Map.class);

        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        assertEquals("Lanche da Tarde", putResponse.getBody().get("nome"));
    }

    @Test
    void deletar_DeveRetornar204() {
        Integer usuarioId = criarUsuario();

        refeicaoRequest = new LinkedHashMap<>();
        refeicaoRequest.put("nome", "Cafe da Manha");
        refeicaoRequest.put("dataRefeicao", "2024-06-15T08:00:00");
        refeicaoRequest.put("usuarioId", usuarioId);

        ResponseEntity<Map> postResponse = restTemplate.postForEntity(
                "/api/v1/refeicoes", refeicaoRequest, Map.class);
        Integer id = (Integer) postResponse.getBody().get("id");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/v1/refeicoes/" + id, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }
}
