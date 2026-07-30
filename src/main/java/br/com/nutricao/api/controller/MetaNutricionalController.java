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
import org.springframework.web.bind.annotation.RestController;

import br.com.nutricao.application.dto.MetaNutricionalRequest;
import br.com.nutricao.application.dto.MetaNutricionalResponse;
import br.com.nutricao.application.service.MetaNutricionalService;
import br.com.nutricao.domain.model.MetaNutricional;
import br.com.nutricao.domain.model.Usuario;

@RestController
@RequestMapping("/api/v1/metas-nutricionais")
public class MetaNutricionalController {

    private final MetaNutricionalService metaNutricionalService;

    public MetaNutricionalController(MetaNutricionalService metaNutricionalService) {
        this.metaNutricionalService = metaNutricionalService;
    }

    @PostMapping
    public ResponseEntity<MetaNutricionalResponse> criar(@RequestBody MetaNutricionalRequest request) {
        MetaNutricional saved = metaNutricionalService.criar(toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<MetaNutricionalResponse>> listar() {
        List<MetaNutricionalResponse> list = metaNutricionalService.listar().stream()
                .map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetaNutricionalResponse> buscarPorId(@PathVariable Integer id) {
        return metaNutricionalService.buscarPorId(id)
                .map(m -> ResponseEntity.ok(toResponse(m)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MetaNutricionalResponse>> buscarPorUsuario(@PathVariable Integer usuarioId) {
        List<MetaNutricionalResponse> list = metaNutricionalService.buscarPorUsuario(usuarioId)
                .stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/usuario/{usuarioId}/ultima")
    public ResponseEntity<MetaNutricionalResponse> buscarUltimaPorUsuario(@PathVariable Integer usuarioId) {
        return metaNutricionalService.buscarUltimaPorUsuario(usuarioId)
                .map(m -> ResponseEntity.ok(toResponse(m)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetaNutricionalResponse> atualizar(@PathVariable Integer id, @RequestBody MetaNutricionalRequest request) {
        MetaNutricional updated = metaNutricionalService.atualizar(id, toEntity(request));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        metaNutricionalService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private MetaNutricional toEntity(MetaNutricionalRequest r) {
        MetaNutricional m = new MetaNutricional();
        if (r.getUsuarioId() != null) {
            m.setUsuario(new Usuario(r.getUsuarioId()));
        }
        m.setCalorias(r.getCalorias());
        m.setProteinas(r.getProteinas());
        m.setCarboidratos(r.getCarboidratos());
        m.setGorduras(r.getGorduras());
        m.setDataInicio(r.getDataInicio());
        m.setDataFim(r.getDataFim());
        return m;
    }

    private MetaNutricionalResponse toResponse(MetaNutricional m) {
        return new MetaNutricionalResponse(m.getId(),
                m.getUsuario() != null ? m.getUsuario().getId() : null,
                m.getCalorias(), m.getProteinas(), m.getCarboidratos(),
                m.getGorduras(), m.getDataInicio(), m.getDataFim());
    }
}
