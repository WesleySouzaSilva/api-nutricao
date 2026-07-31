package br.com.nutricao.controller;

import br.com.nutricao.controller.documentacao.ObjetivoControllerSwagger;
import java.util.List;
import java.util.stream.Collectors;

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

import br.com.nutricao.domain.dto.filtro.ObjetivoCamposFiltro;
import br.com.nutricao.domain.dto.insercao.ObjetivoRequest;
import br.com.nutricao.domain.dto.visualizacao.ObjetivoResponse;
import br.com.nutricao.services.ObjetivoService;
import br.com.nutricao.domain.Objetivo;
import br.com.nutricao.domain.Usuario;

@RestController
@RequestMapping("/api/v1/objetivos")
public class ObjetivoController implements ObjetivoControllerSwagger {

    private final ObjetivoService objetivoService;

    public ObjetivoController(ObjetivoService objetivoService) {
        this.objetivoService = objetivoService;
    }

    @PostMapping
    public ResponseEntity<ObjetivoResponse> criar(@RequestBody ObjetivoRequest request) {
        Objetivo saved = objetivoService.criar(toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<Page<ObjetivoResponse>> listar(ObjetivoCamposFiltro filtro, Pageable pageable) {
        Page<Objetivo> page = objetivoService.listarTodosFiltro(filtro, pageable);
        Page<ObjetivoResponse> responsePage = page.map(this::toResponse);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjetivoResponse> buscarPorId(@PathVariable Integer id) {
        return objetivoService.buscarPorId(id)
                .map(o -> ResponseEntity.ok(toResponse(o)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ObjetivoResponse>> buscarPorUsuario(@PathVariable Integer usuarioId) {
        List<ObjetivoResponse> list = objetivoService.buscarPorUsuario(usuarioId)
                .stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjetivoResponse> atualizar(@PathVariable Integer id, @RequestBody ObjetivoRequest request) {
        Objetivo updated = objetivoService.atualizar(id, toEntity(request));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        objetivoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private Objetivo toEntity(ObjetivoRequest r) {
        Objetivo o = new Objetivo();
        if (r.getUsuarioId() != null) {
            o.setUsuario(new Usuario(r.getUsuarioId()));
        }
        o.setTipo(r.getTipo());
        o.setPesoAlvo(r.getPesoAlvo());
        o.setCaloriasDiarias(r.getCaloriasDiarias());
        o.setDataInicio(r.getDataInicio());
        o.setDataFim(r.getDataFim());
        o.setDescricao(r.getDescricao());
        return o;
    }

    private ObjetivoResponse toResponse(Objetivo o) {
        return new ObjetivoResponse(o.getId(),
                o.getUsuario() != null ? o.getUsuario().getId() : null,
                o.getTipo(), o.getPesoAlvo(), o.getCaloriasDiarias(),
                o.getDataInicio(), o.getDataFim(), o.getDescricao());
    }
}
