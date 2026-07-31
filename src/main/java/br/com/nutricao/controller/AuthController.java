package br.com.nutricao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nutricao.domain.dto.visualizacao.AuthResponseDTO;
import br.com.nutricao.domain.dto.visualizacao.Login;
import br.com.nutricao.domain.dto.visualizacao.LoginToken;
import br.com.nutricao.security.JWTUtil;
import br.com.nutricao.security.UsuarioDetails;
import br.com.nutricao.security.UsuarioDetailsService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UsuarioDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
                          UsuarioDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody Login request) {
        try {
            var userDetails = (UsuarioDetails) userDetailsService.loadUserByUsername(request.getEmail());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));

            var usuario = userDetails.getUsuario();
            String token = jwtUtil.generateToken(usuario.getEmail());

            AuthResponseDTO response = new AuthResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(),
                    token, "Bearer", usuario.getSexo(), "Token gerado com sucesso!");

            return ResponseEntity.ok(response);

        } catch (UsernameNotFoundException e) {
            AuthResponseDTO response = new AuthResponseDTO(null, "", request.getEmail(), "", "",
                    "", "ERRO: E-mail nao encontrado na base de dados");
            return ResponseEntity.badRequest().body(response);

        } catch (BadCredentialsException e) {
            AuthResponseDTO response = new AuthResponseDTO(null, "", request.getEmail(), "", "",
                    "", "ERRO: Senha incorreta para o e-mail fornecido");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login/token_id")
    public ResponseEntity<AuthResponseDTO> loginToken(@RequestBody LoginToken loginToken) {
        try {
            if (loginToken.getTokenId() == null || loginToken.getTokenId().isBlank()) {
                AuthResponseDTO response = new AuthResponseDTO(null, "", "", "", "", "",
                        "ERRO: Token ID nao informado");
                return ResponseEntity.badRequest().body(response);
            }

            var userDetails = (UsuarioDetails) userDetailsService.loadUserByTokenId(loginToken.getTokenId());
            var usuario = userDetails.getUsuario();

            String token = jwtUtil.generateToken(usuario.getEmail());

            AuthResponseDTO response = new AuthResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(),
                    token, "Bearer", usuario.getSexo(), "Token gerado com sucesso!");

            return ResponseEntity.ok(response);

        } catch (UsernameNotFoundException e) {
            AuthResponseDTO response = new AuthResponseDTO(null, "", "", "", "", "",
                    "ERRO: Token invalido");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
