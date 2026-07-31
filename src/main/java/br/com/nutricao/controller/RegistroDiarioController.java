package br.com.nutricao.controller;

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

import br.com.nutricao.domain.dto.filtro.RegistroDiarioCamposFiltro;
import br.com.nutricao.domain.dto.insercao.RegistroDiarioRequest;
import br.com.nutricao.domain.dto.visualizacao.RegistroDiarioResponse;
import br.com.nutricao.services.RegistroDiarioService;
import br.com.nutricao.domain.RegistroDiario;
import br.com.nutricao.domain.Usuario;

@RestController
@RequestMapping("/api/v1/registros-diarios")
public class RegistroDiarioController {

    private final RegistroDiarioService registroDiarioService;

    public RegistroDiarioController(RegistroDiarioService registroDiarioService) {
        this.registroDiarioService = registroDiarioService;
    }

    @PostMapping
    public ResponseEntity<RegistroDiarioResponse> criar(@RequestBody RegistroDiarioRequest request) {
        RegistroDiario saved = registroDiarioService.criar(toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<Page<RegistroDiarioResponse>> listar(RegistroDiarioCamposFiltro filtro, Pageable pageable) {
        Page<RegistroDiario> page = registroDiarioService.listarTodosFiltro(filtro, pageable);
        Page<RegistroDiarioResponse> responsePage = page.map(this::toResponse);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroDiarioResponse> buscarPorId(@PathVariable Integer id) {
        return registroDiarioService.buscarPorId(id)
                .map(r -> ResponseEntity.ok(toResponse(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroDiarioResponse> atualizar(@PathVariable Integer id, @RequestBody RegistroDiarioRequest request) {
        RegistroDiario updated = registroDiarioService.atualizar(id, toEntity(request));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        registroDiarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private RegistroDiario toEntity(RegistroDiarioRequest r) {
        RegistroDiario reg = new RegistroDiario();
        if (r.getUsuarioId() != null) {
            reg.setUsuario(new Usuario(r.getUsuarioId()));
        }
        reg.setData(r.getData());
        reg.setCaloriasConsumidas(r.getCaloriasConsumidas());
        reg.setProteinasConsumidas(r.getProteinasConsumidas());
        reg.setCarboidratosConsumidos(r.getCarboidratosConsumidos());
        reg.setGordurasConsumidas(r.getGordurasConsumidas());
        reg.setObservacoes(r.getObservacoes());
        return reg;
    }

    private RegistroDiarioResponse toResponse(RegistroDiario r) {
        return new RegistroDiarioResponse(r.getId(),
                r.getUsuario() != null ? r.getUsuario().getId() : null,
                r.getData(), r.getCaloriasConsumidas(), r.getProteinasConsumidas(),
                r.getCarboidratosConsumidos(), r.getGordurasConsumidas(), r.getObservacoes());
    }
}
