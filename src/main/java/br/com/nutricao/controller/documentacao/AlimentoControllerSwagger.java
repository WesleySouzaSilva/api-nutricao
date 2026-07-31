package br.com.nutricao.controller.documentacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.nutricao.domain.dto.filtro.AlimentoCamposFiltro;
import br.com.nutricao.domain.dto.insercao.AlimentoRequest;
import br.com.nutricao.domain.dto.visualizacao.AlimentoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Alimentos")
public interface AlimentoControllerSwagger {

    @Operation(summary = "Cadastrar novo alimento", description = "Cadastra um novo alimento com sua tabela nutricional (calorias, macronutrientes, fibra, sodio)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alimento cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos — nome duplicado, campos obrigatorios ausentes ou categoria inexistente")
    })
    ResponseEntity<AlimentoResponse> criar(@RequestBody AlimentoRequest request);

    @Operation(summary = "Listar alimentos", description = "Retorna a lista paginada de alimentos com suporte a filtros por nome e categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de alimentos retornada com sucesso")
    })
    ResponseEntity<Page<AlimentoResponse>> listar(
            @Parameter(description = "Filtros: nome (string), categoriaAlimento (integer)") AlimentoCamposFiltro filtro,
            @Parameter(description = "Parametros de paginacao (page, size, sort)") Pageable pageable);

    @Operation(summary = "Buscar alimento por ID", description = "Retorna os dados completos de um alimento incluindo sua informacao nutricional")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alimento encontrado"),
            @ApiResponse(responseCode = "404", description = "Alimento nao encontrado para o ID informado")
    })
    ResponseEntity<AlimentoResponse> buscarPorId(
            @Parameter(description = "ID do alimento") @PathVariable Integer id);

    @Operation(summary = "Atualizar alimento", description = "Atualiza os dados e a tabela nutricional de um alimento existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alimento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alimento nao encontrado para o ID informado"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos — nome duplicado ou categoria inexistente")
    })
    ResponseEntity<AlimentoResponse> atualizar(
            @Parameter(description = "ID do alimento") @PathVariable Integer id,
            @RequestBody AlimentoRequest request);

    @Operation(summary = "Remover alimento", description = "Remove permanentemente um alimento da base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alimento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alimento nao encontrado para o ID informado")
    })
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID do alimento") @PathVariable Integer id);
}
