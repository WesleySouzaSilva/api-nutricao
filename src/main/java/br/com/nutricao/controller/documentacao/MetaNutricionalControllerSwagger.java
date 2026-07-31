package br.com.nutricao.controller.documentacao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.nutricao.domain.dto.filtro.MetaNutricionalCamposFiltro;
import br.com.nutricao.domain.dto.insercao.MetaNutricionalRequest;
import br.com.nutricao.domain.dto.visualizacao.MetaNutricionalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Metas Nutricionais")
public interface MetaNutricionalControllerSwagger {

    @Operation(summary = "Criar nova meta nutricional", description = "Define metas diarias de calorias e macronutrientes (proteinas, carboidratos, gorduras) para um periodo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Meta nutricional criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos — usuario inexistente ou campos obrigatorios ausentes")
    })
    ResponseEntity<MetaNutricionalResponse> criar(@RequestBody MetaNutricionalRequest request);

    @Operation(summary = "Listar metas nutricionais", description = "Retorna a lista paginada de metas nutricionais com suporte a filtros por usuario e periodo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de metas retornada com sucesso")
    })
    ResponseEntity<Page<MetaNutricionalResponse>> listar(
            @Parameter(description = "Filtros: usuarioId (integer), dataInicio (date), dataFim (date)") MetaNutricionalCamposFiltro filtro,
            @Parameter(description = "Parametros de paginacao (page, size, sort)") Pageable pageable);

    @Operation(summary = "Buscar meta por ID", description = "Retorna os dados de uma meta nutricional especifica pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meta encontrada"),
            @ApiResponse(responseCode = "404", description = "Meta nao encontrada para o ID informado")
    })
    ResponseEntity<MetaNutricionalResponse> buscarPorId(
            @Parameter(description = "ID da meta nutricional") @PathVariable Integer id);

    @Operation(summary = "Listar metas de um usuario", description = "Retorna todas as metas nutricionais cadastradas para um usuario especifico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de metas do usuario retornada com sucesso")
    })
    ResponseEntity<List<MetaNutricionalResponse>> buscarPorUsuario(
            @Parameter(description = "ID do usuario") @PathVariable Integer usuarioId);

    @Operation(summary = "Buscar ultima meta do usuario", description = "Retorna a meta nutricional mais recente cadastrada para o usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ultima meta do usuario retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma meta encontrada para o usuario")
    })
    ResponseEntity<MetaNutricionalResponse> buscarUltimaPorUsuario(
            @Parameter(description = "ID do usuario") @PathVariable Integer usuarioId);

    @Operation(summary = "Atualizar meta nutricional", description = "Atualiza os valores de uma meta nutricional existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meta atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Meta nao encontrada para o ID informado"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos")
    })
    ResponseEntity<MetaNutricionalResponse> atualizar(
            @Parameter(description = "ID da meta nutricional") @PathVariable Integer id,
            @RequestBody MetaNutricionalRequest request);

    @Operation(summary = "Remover meta nutricional", description = "Remove permanentemente uma meta nutricional da base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Meta removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Meta nao encontrada para o ID informado")
    })
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID da meta nutricional") @PathVariable Integer id);
}
