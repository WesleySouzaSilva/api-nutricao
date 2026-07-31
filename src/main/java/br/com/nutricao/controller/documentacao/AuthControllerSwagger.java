package br.com.nutricao.controller.documentacao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.nutricao.domain.dto.visualizacao.AuthResponseDTO;
import br.com.nutricao.domain.dto.visualizacao.Login;
import br.com.nutricao.domain.dto.visualizacao.LoginToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticacao")
public interface AuthControllerSwagger {

    @Operation(summary = "Autenticar usuario", description = "Realiza login com email e senha e retorna token JWT para acesso aos endpoints protegidos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso — retorna token JWT e dados do usuario"),
            @ApiResponse(responseCode = "400", description = "Credenciais invalidas — email nao encontrado ou senha incorreta")
    })
    ResponseEntity<AuthResponseDTO> login(@RequestBody Login request);

    @Operation(summary = "Validar token JWT", description = "Valida um token JWT existente e retorna os dados do usuario associado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token valido — retorna token renovado e dados do usuario"),
            @ApiResponse(responseCode = "400", description = "Token invalido ou nao informado")
    })
    ResponseEntity<AuthResponseDTO> loginToken(@RequestBody LoginToken loginToken);
}
