package br.com.nutricao.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nutricao.domain.dto.insercao.AlimentoFavoritoRequest;
import br.com.nutricao.domain.dto.visualizacao.AlimentoFavoritoResponse;
import br.com.nutricao.services.AlimentoFavoritoService;
import br.com.nutricao.domain.Alimento;
import br.com.nutricao.domain.AlimentoFavorito;
import br.com.nutricao.domain.Usuario;

@RestController
@RequestMapping("/api/v1/favoritos")
public class AlimentoFavoritoController {

    private final AlimentoFavoritoService alimentoFavoritoService;

    public AlimentoFavoritoController(AlimentoFavoritoService alimentoFavoritoService) {
        this.alimentoFavoritoService = alimentoFavoritoService;
    }

    @PostMapping
    public ResponseEntity<AlimentoFavoritoResponse> adicionar(@RequestBody AlimentoFavoritoRequest request) {
        AlimentoFavorito favorito = new AlimentoFavorito();
        if (request.getUsuarioId() != null) {
            favorito.setUsuario(new Usuario(request.getUsuarioId()));
        }
        if (request.getAlimentoId() != null) {
            favorito.setAlimento(new Alimento(request.getAlimentoId()));
        }
        favorito.setDataAdicao(LocalDateTime.now());
        AlimentoFavorito saved = alimentoFavoritoService.adicionar(favorito);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AlimentoFavoritoResponse>> buscarPorUsuario(@PathVariable Integer usuarioId) {
        List<AlimentoFavoritoResponse> list = alimentoFavoritoService.buscarPorUsuario(usuarioId)
                .stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/usuario/{usuarioId}/alimento/{alimentoId}")
    public ResponseEntity<Void> remover(@PathVariable Integer usuarioId, @PathVariable Integer alimentoId) {
        alimentoFavoritoService.remover(usuarioId, alimentoId);
        return ResponseEntity.noContent().build();
    }

    private AlimentoFavoritoResponse toResponse(AlimentoFavorito f) {
        return new AlimentoFavoritoResponse(f.getId(),
                f.getUsuario() != null ? f.getUsuario().getId() : null,
                f.getAlimento() != null ? f.getAlimento().getId() : null,
                f.getDataAdicao());
    }
}
