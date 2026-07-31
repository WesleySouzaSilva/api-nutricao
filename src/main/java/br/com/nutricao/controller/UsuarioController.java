package br.com.nutricao.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.nutricao.controller.documentacao.UsuarioControllerSwagger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nutricao.domain.dto.insercao.UsuarioRequest;
import br.com.nutricao.domain.dto.visualizacao.UsuarioResponse;
import br.com.nutricao.services.UsuarioService;
import br.com.nutricao.domain.Usuario;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController implements UsuarioControllerSwagger {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@RequestBody UsuarioRequest request) {
        Usuario usuario = toEntity(request);
        usuario.setDataCadastro(LocalDateTime.now());
        Usuario saved = usuarioService.criar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        List<UsuarioResponse> list = usuarioService.listar().stream()
                .map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id)
                .map(u -> ResponseEntity.ok(toResponse(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponse> buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email)
                .map(u -> ResponseEntity.ok(toResponse(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Integer id, @RequestBody UsuarioRequest request) {
        Usuario usuario = toEntity(request);
        Usuario updated = usuarioService.atualizar(id, usuario);
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private Usuario toEntity(UsuarioRequest r) {
        Usuario u = new Usuario();
        u.setNome(r.getNome());
        u.setSenha(r.getSenha());
        u.setEmail(r.getEmail());
        u.setDataNascimento(r.getDataNascimento());
        u.setAltura(r.getAltura());
        u.setSexo(r.getSexo());
        u.setMedida(r.getMedida());
        u.setTipoLogin(r.getTipoLogin());
        return u;
    }

    private UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNome(), u.getEmail(),
                u.getDataNascimento(), u.getAltura(), u.getSexo(),
                u.getMedida(), u.getTipoLogin(), u.getDataCadastro());
    }
}
