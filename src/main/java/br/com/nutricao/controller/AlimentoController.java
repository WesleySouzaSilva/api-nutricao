package br.com.nutricao.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.nutricao.application.dto.AlimentoRequest;
import br.com.nutricao.application.dto.AlimentoResponse;
import br.com.nutricao.application.dto.CategoriaAlimentoResponse;
import br.com.nutricao.application.service.AlimentoService;
import br.com.nutricao.domain.model.Alimento;
import br.com.nutricao.domain.model.CategoriaAlimento;

@RestController
@RequestMapping("/api/v1/alimentos")
public class AlimentoController {

    private final AlimentoService alimentoService;

    public AlimentoController(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    @PostMapping
    public ResponseEntity<AlimentoResponse> criar(@RequestBody AlimentoRequest request) {
        Alimento saved = alimentoService.criar(toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<AlimentoResponse>> listar(
            @RequestParam(required = false) Integer categoriaId,
            @RequestParam(required = false) String nome) {
        List<Alimento> result;
        if (categoriaId != null) {
            result = alimentoService.buscarPorCategoria(categoriaId);
        } else if (nome != null) {
            result = alimentoService.buscarPorNome(nome);
        } else {
            result = alimentoService.listar();
        }
        List<AlimentoResponse> list = result.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimentoResponse> buscarPorId(@PathVariable Integer id) {
        return alimentoService.buscarPorId(id)
                .map(a -> ResponseEntity.ok(toResponse(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlimentoResponse> atualizar(@PathVariable Integer id, @RequestBody AlimentoRequest request) {
        Alimento updated = alimentoService.atualizar(id, toEntity(request));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        alimentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private Alimento toEntity(AlimentoRequest r) {
        Alimento a = new Alimento();
        a.setNome(r.getNome());
        a.setKcal(r.getKcal());
        a.setProteina(r.getProteina());
        a.setGordura(r.getGordura());
        a.setCarboidrato(r.getCarboidrato());
        a.setFibraAlimentar(r.getFibraAlimentar());
        a.setSodio(r.getSodio());
        if (r.getCategoriaAlimentoId() != null) {
            a.setCategoriaAlimento(new CategoriaAlimento(r.getCategoriaAlimentoId()));
        }
        return a;
    }

    private AlimentoResponse toResponse(Alimento a) {
        CategoriaAlimentoResponse cat = a.getCategoriaAlimento() != null
                ? new CategoriaAlimentoResponse(a.getCategoriaAlimento().getId(), a.getCategoriaAlimento().getNome())
                : null;
        return new AlimentoResponse(a.getId(), a.getNome(), a.getKcal(), a.getProteina(),
                a.getGordura(), a.getCarboidrato(), a.getFibraAlimentar(), a.getSodio(), cat);
    }
}
