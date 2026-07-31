package br.com.nutricao.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
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

import br.com.nutricao.domain.dto.insercao.RefeicaoRequest;
import br.com.nutricao.domain.dto.visualizacao.RefeicaoResponse;
import br.com.nutricao.services.RefeicaoService;
import br.com.nutricao.domain.Refeicao;
import br.com.nutricao.domain.Usuario;

@RestController
@RequestMapping("/api/v1/refeicoes")
public class RefeicaoController {

    private final RefeicaoService refeicaoService;

    public RefeicaoController(RefeicaoService refeicaoService) {
        this.refeicaoService = refeicaoService;
    }

    @PostMapping
    public ResponseEntity<RefeicaoResponse> criar(@RequestBody RefeicaoRequest request) {
        Refeicao saved = refeicaoService.criar(toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<RefeicaoResponse>> listar(
            @RequestParam(required = false) Integer usuarioId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<Refeicao> result;
        if (usuarioId != null && inicio != null && fim != null) {
            result = refeicaoService.buscarPorUsuarioEPeriodo(usuarioId, inicio, fim);
        } else if (usuarioId != null) {
            result = refeicaoService.buscarPorUsuario(usuarioId);
        } else {
            result = refeicaoService.listar();
        }
        List<RefeicaoResponse> list = result.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefeicaoResponse> buscarPorId(@PathVariable Integer id) {
        return refeicaoService.buscarPorId(id)
                .map(r -> ResponseEntity.ok(toResponse(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RefeicaoResponse> atualizar(@PathVariable Integer id, @RequestBody RefeicaoRequest request) {
        Refeicao updated = refeicaoService.atualizar(id, toEntity(request));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        refeicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private Refeicao toEntity(RefeicaoRequest r) {
        Refeicao ref = new Refeicao();
        ref.setNome(r.getNome());
        ref.setDataRefeicao(r.getDataRefeicao());
        if (r.getUsuarioId() != null) {
            ref.setUsuario(new Usuario(r.getUsuarioId()));
        }
        return ref;
    }

    private RefeicaoResponse toResponse(Refeicao r) {
        return new RefeicaoResponse(r.getId(), r.getNome(), r.getDataRefeicao(),
                r.getUsuario() != null ? r.getUsuario().getId() : null);
    }
}
