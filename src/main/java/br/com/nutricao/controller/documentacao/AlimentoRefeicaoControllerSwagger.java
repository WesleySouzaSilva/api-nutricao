package br.com.nutricao.controller.documentacao;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.nutricao.domain.dto.insercao.AlimentoRefeicaoRequest;
import br.com.nutricao.domain.dto.visualizacao.AlimentoRefeicaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Alimentos da Refeicao")
public interface AlimentoRefeicaoControllerSwagger {

    @Operation(summary = "Adicionar alimento a uma refeicao", description = "Associa um alimento com sua quantidade e porcao a uma refeicao existente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alimento adicionado a refeicao com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos — refeicao ou alimento inexistente, campos obrigatorios ausentes")
    })
    ResponseEntity<AlimentoRefeicaoResponse> criar(@RequestBody AlimentoRefeicaoRequest request);

    @Operation(summary = "Listar alimentos de uma refeicao", description = "Retorna todos os alimentos associados a uma refeicao especifica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de alimentos da refeicao retornada com sucesso")
    })
    ResponseEntity<List<AlimentoRefeicaoResponse>> buscarPorRefeicao(
            @Parameter(description = "ID da refeicao") @PathVariable Integer refeicaoId);

    @Operation(summary = "Remover alimento da refeicao", description = "Remove um alimento especifico de uma refeicao pelo ID da associacao")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alimento removido da refeicao com sucesso"),
            @ApiResponse(responseCode = "404", description = "Associacao nao encontrada para o ID informado")
    })
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID da associacao alimento-refeicao") @PathVariable Integer id);

    @Operation(summary = "Remover todos os alimentos de uma refeicao", description = "Remove todos os alimentos associados a uma refeicao de uma unica vez")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Todos os alimentos removidos da refeicao com sucesso"),
            @ApiResponse(responseCode = "404", description = "Refeicao nao encontrada para o ID informado")
    })
    ResponseEntity<Void> deletarPorRefeicao(
            @Parameter(description = "ID da refeicao") @PathVariable Integer refeicaoId);
}
