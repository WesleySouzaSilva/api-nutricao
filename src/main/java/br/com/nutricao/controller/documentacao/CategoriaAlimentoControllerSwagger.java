package br.com.nutricao.controller.documentacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.nutricao.domain.dto.insercao.CategoriaAlimentoRequest;
import br.com.nutricao.domain.dto.visualizacao.CategoriaAlimentoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Categorias de Alimentos")
public interface CategoriaAlimentoControllerSwagger {

    @Operation(summary = "Criar nova categoria", description = "Cadastra uma nova categoria de alimentos (ex: Cereais, Legumes, Frutas)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos — nome duplicado ou campo obrigatorio ausente")
    })
    ResponseEntity<CategoriaAlimentoResponse> criar(@RequestBody CategoriaAlimentoRequest request);

    @Operation(summary = "Listar categorias", description = "Retorna a lista paginada de categorias de alimentos cadastradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso")
    })
    ResponseEntity<Page<CategoriaAlimentoResponse>> listar(
            @Parameter(description = "Parametros de paginacao (page, size, sort)") Pageable pageable);

    @Operation(summary = "Buscar categoria por ID", description = "Retorna os dados de uma categoria especifica pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria nao encontrada para o ID informado")
    })
    ResponseEntity<CategoriaAlimentoResponse> buscarPorId(
            @Parameter(description = "ID da categoria") @PathVariable Integer id);

    @Operation(summary = "Atualizar categoria", description = "Atualiza o nome de uma categoria de alimentos existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria nao encontrada para o ID informado"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos — nome duplicado ou formato invalido")
    })
    ResponseEntity<CategoriaAlimentoResponse> atualizar(
            @Parameter(description = "ID da categoria") @PathVariable Integer id,
            @RequestBody CategoriaAlimentoRequest request);

    @Operation(summary = "Remover categoria", description = "Remove permanentemente uma categoria de alimentos da base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria nao encontrada para o ID informado"),
            @ApiResponse(responseCode = "409", description = "Categoria possui alimentos vinculados e nao pode ser removida")
    })
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID da categoria") @PathVariable Integer id);
}
