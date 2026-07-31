package br.com.nutricao.api.controller;

import br.com.nutricao.application.dto.AuthRequest;
import br.com.nutricao.application.dto.AuthResponse;
import br.com.nutricao.application.dto.LoginToken;
import br.com.nutricao.config.JwtUtil;
import br.com.nutricao.config.UsuarioDetails;
import br.com.nutricao.config.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            var userDetails = (UsuarioDetails) userDetailsService.loadUserByUsername(request.getEmail());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));

            var usuario = userDetails.getUsuario();
            String token = jwtUtil.generateToken(usuario.getEmail());

            AuthResponse response = new AuthResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(),
                    token, "Bearer", usuario.getSexo(), "Token gerado com sucesso!");

            return ResponseEntity.ok(response);

        } catch (UsernameNotFoundException e) {
            AuthResponse response = new AuthResponse(null, "", request.getEmail(), "", "",
                    "", "ERRO: E-mail nao encontrado na base de dados");
            return ResponseEntity.badRequest().body(response);

        } catch (BadCredentialsException e) {
            AuthResponse response = new AuthResponse(null, "", request.getEmail(), "", "",
                    "", "ERRO: Senha incorreta para o e-mail fornecido");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login/token_id")
    public ResponseEntity<AuthResponse> loginToken(@RequestBody LoginToken loginToken) {
        try {
            if (loginToken.getTokenId() == null || loginToken.getTokenId().isBlank()) {
                AuthResponse response = new AuthResponse(null, "", "", "", "", "",
                        "ERRO: Token ID nao informado");
                return ResponseEntity.badRequest().body(response);
            }

            var userDetails = (UsuarioDetails) userDetailsService.loadUserByTokenId(loginToken.getTokenId());
            var usuario = userDetails.getUsuario();

            String token = jwtUtil.generateToken(usuario.getEmail());

            AuthResponse response = new AuthResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(),
                    token, "Bearer", usuario.getSexo(), "Token gerado com sucesso!");

            return ResponseEntity.ok(response);

        } catch (UsernameNotFoundException e) {
            AuthResponse response = new AuthResponse(null, "", "", "", "", "",
                    "ERRO: Token invalido");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
