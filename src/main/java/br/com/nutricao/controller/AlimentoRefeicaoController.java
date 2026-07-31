package br.com.nutricao.controller;

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

import br.com.nutricao.domain.dto.insercao.AlimentoRefeicaoRequest;
import br.com.nutricao.domain.dto.visualizacao.AlimentoRefeicaoResponse;
import br.com.nutricao.services.AlimentoRefeicaoService;
import br.com.nutricao.domain.Alimento;
import br.com.nutricao.domain.AlimentoRefeicao;
import br.com.nutricao.domain.Refeicao;

@RestController
@RequestMapping("/api/v1/alimentos-refeicao")
public class AlimentoRefeicaoController {

    private final AlimentoRefeicaoService alimentoRefeicaoService;

    public AlimentoRefeicaoController(AlimentoRefeicaoService alimentoRefeicaoService) {
        this.alimentoRefeicaoService = alimentoRefeicaoService;
    }

    @PostMapping
    public ResponseEntity<AlimentoRefeicaoResponse> criar(@RequestBody AlimentoRefeicaoRequest request) {
        AlimentoRefeicao saved = alimentoRefeicaoService.criar(toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @GetMapping("/refeicao/{refeicaoId}")
    public ResponseEntity<List<AlimentoRefeicaoResponse>> buscarPorRefeicao(@PathVariable Integer refeicaoId) {
        List<AlimentoRefeicaoResponse> list = alimentoRefeicaoService.buscarPorRefeicao(refeicaoId)
                .stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        alimentoRefeicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/refeicao/{refeicaoId}")
    public ResponseEntity<Void> deletarPorRefeicao(@PathVariable Integer refeicaoId) {
        alimentoRefeicaoService.deletarPorRefeicao(refeicaoId);
        return ResponseEntity.noContent().build();
    }

    private AlimentoRefeicao toEntity(AlimentoRefeicaoRequest r) {
        AlimentoRefeicao ar = new AlimentoRefeicao();
        if (r.getRefeicaoId() != null) {
            ar.setRefeicao(new Refeicao(r.getRefeicaoId()));
        }
        if (r.getAlimentoId() != null) {
            ar.setAlimento(new Alimento(r.getAlimentoId()));
        }
        ar.setQuantidade(r.getQuantidade());
        ar.setPorcao(r.getPorcao());
        return ar;
    }

    private AlimentoRefeicaoResponse toResponse(AlimentoRefeicao ar) {
        return new AlimentoRefeicaoResponse(ar.getId(),
                ar.getRefeicao() != null ? ar.getRefeicao().getId() : null,
                ar.getAlimento() != null ? ar.getAlimento().getId() : null,
                ar.getQuantidade(), ar.getPorcao());
    }
}
