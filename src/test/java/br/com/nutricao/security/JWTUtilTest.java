package br.com.nutricao.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JWTUtilTest {

    private final JWTUtil jwtUtil = new JWTUtil("test-secret-key", 3600000);

    @Test
    void generateToken_DeveRetornarTokenValido() {
        String email = "teste@email.com";
        String token = jwtUtil.generateToken(email);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void validateToken_ComTokenValido_DeveRetornarEmail() {
        String email = "teste@email.com";
        String token = jwtUtil.generateToken(email);

        String result = jwtUtil.validateToken(token);

        assertEquals(email, result);
    }

    @Test
    void validateToken_ComTokenInvalido_DeveRetornarNull() {
        String result = jwtUtil.validateToken("token-invalido");

        assertNull(result);
    }
}
