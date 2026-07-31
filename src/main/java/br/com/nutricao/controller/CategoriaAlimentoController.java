package br.com.nutricao.controller;

import java.util.stream.Collectors;

import br.com.nutricao.controller.documentacao.CategoriaAlimentoControllerSwagger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import br.com.nutricao.domain.dto.insercao.CategoriaAlimentoRequest;
import br.com.nutricao.domain.dto.visualizacao.CategoriaAlimentoResponse;
import br.com.nutricao.services.CategoriaAlimentoService;
import br.com.nutricao.domain.CategoriaAlimento;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaAlimentoController implements CategoriaAlimentoControllerSwagger {

    private final CategoriaAlimentoService categoriaAlimentoService;

    public CategoriaAlimentoController(CategoriaAlimentoService categoriaAlimentoService) {
        this.categoriaAlimentoService = categoriaAlimentoService;
    }

    @PostMapping
    public ResponseEntity<CategoriaAlimentoResponse> criar(@RequestBody CategoriaAlimentoRequest request) {
        CategoriaAlimento saved = categoriaAlimentoService.criar(new CategoriaAlimento(null, request.getNome()));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaAlimentoResponse>> listar(Pageable pageable) {
        Page<CategoriaAlimento> page = categoriaAlimentoService.listarPaginado(pageable);
        Page<CategoriaAlimentoResponse> responsePage = page.map(this::toResponse);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaAlimentoResponse> buscarPorId(@PathVariable Integer id) {
        return categoriaAlimentoService.buscarPorId(id)
                .map(c -> ResponseEntity.ok(toResponse(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaAlimentoResponse> atualizar(@PathVariable Integer id, @RequestBody CategoriaAlimentoRequest request) {
        CategoriaAlimento updated = categoriaAlimentoService.atualizar(id, new CategoriaAlimento(null, request.getNome()));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        categoriaAlimentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private CategoriaAlimentoResponse toResponse(CategoriaAlimento c) {
        return new CategoriaAlimentoResponse(c.getId(), c.getNome());
    }
}
