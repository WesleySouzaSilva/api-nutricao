package br.com.nutricao.controller.documentacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.nutricao.domain.dto.filtro.RefeicaoCamposFiltro;
import br.com.nutricao.domain.dto.insercao.RefeicaoRequest;
import br.com.nutricao.domain.dto.visualizacao.RefeicaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Refeicoes")
public interface RefeicaoControllerSwagger {

    @Operation(summary = "Criar nova refeicao", description = "Registra uma nova refeicao (cafe da manha, almoco, jantar, lanche) para um usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Refeicao criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos — campos obrigatorios ausentes ou usuario inexistente")
    })
    ResponseEntity<RefeicaoResponse> criar(@RequestBody RefeicaoRequest request);

    @Operation(summary = "Listar refeicoes", description = "Retorna a lista paginada de refeicoes com suporte a filtros por usuario e periodo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de refeicoes retornada com sucesso")
    })
    ResponseEntity<Page<RefeicaoResponse>> listar(
            @Parameter(description = "Filtros: usuarioId (integer), dataInicio (date), dataFim (date)") RefeicaoCamposFiltro filtro,
            @Parameter(description = "Parametros de paginacao (page, size, sort)") Pageable pageable);

    @Operation(summary = "Buscar refeicao por ID", description = "Retorna os dados de uma refeicao especifica pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Refeicao encontrada"),
            @ApiResponse(responseCode = "404", description = "Refeicao nao encontrada para o ID informado")
    })
    ResponseEntity<RefeicaoResponse> buscarPorId(
            @Parameter(description = "ID da refeicao") @PathVariable Integer id);

    @Operation(summary = "Atualizar refeicao", description = "Atualiza os dados de uma refeicao existente (nome, data, horario)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Refeicao atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Refeicao nao encontrada para o ID informado"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos")
    })
    ResponseEntity<RefeicaoResponse> atualizar(
            @Parameter(description = "ID da refeicao") @PathVariable Integer id,
            @RequestBody RefeicaoRequest request);

    @Operation(summary = "Remover refeicao", description = "Remove permanentemente uma refeicao e seus alimentos associados da base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Refeicao removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Refeicao nao encontrada para o ID informado")
    })
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID da refeicao") @PathVariable Integer id);
}
