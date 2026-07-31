package br.com.nutricao.controller.documentacao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.nutricao.domain.dto.filtro.ObjetivoCamposFiltro;
import br.com.nutricao.domain.dto.insercao.ObjetivoRequest;
import br.com.nutricao.domain.dto.visualizacao.ObjetivoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Objetivos")
public interface ObjetivoControllerSwagger {

    @Operation(summary = "Criar novo objetivo", description = "Define um objetivo (ganhar massa, reduzir gordura, manter peso) com peso alvo e calorias diarias para um periodo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Objetivo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos — usuario inexistente, campos obrigatorios ausentes ou tipo invalido")
    })
    ResponseEntity<ObjetivoResponse> criar(@RequestBody ObjetivoRequest request);

    @Operation(summary = "Listar objetivos", description = "Retorna a lista paginada de objetivos com suporte a filtros por usuario, tipo e periodo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de objetivos retornada com sucesso")
    })
    ResponseEntity<Page<ObjetivoResponse>> listar(
            @Parameter(description = "Filtros: usuarioId (integer), tipo (string), dataInicio (date), dataFim (date)") ObjetivoCamposFiltro filtro,
            @Parameter(description = "Parametros de paginacao (page, size, sort)") Pageable pageable);

    @Operation(summary = "Buscar objetivo por ID", description = "Retorna os dados de um objetivo especifico pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objetivo encontrado"),
            @ApiResponse(responseCode = "404", description = "Objetivo nao encontrado para o ID informado")
    })
    ResponseEntity<ObjetivoResponse> buscarPorId(
            @Parameter(description = "ID do objetivo") @PathVariable Integer id);

    @Operation(summary = "Listar objetivos de um usuario", description = "Retorna todos os objetivos cadastrados para um usuario especifico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de objetivos do usuario retornada com sucesso")
    })
    ResponseEntity<List<ObjetivoResponse>> buscarPorUsuario(
            @Parameter(description = "ID do usuario") @PathVariable Integer usuarioId);

    @Operation(summary = "Atualizar objetivo", description = "Atualiza os dados de um objetivo existente (tipo, peso alvo, calorias, periodo)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objetivo atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Objetivo nao encontrado para o ID informado"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos")
    })
    ResponseEntity<ObjetivoResponse> atualizar(
            @Parameter(description = "ID do objetivo") @PathVariable Integer id,
            @RequestBody ObjetivoRequest request);

    @Operation(summary = "Remover objetivo", description = "Remove permanentemente um objetivo da base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Objetivo removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Objetivo nao encontrado para o ID informado")
    })
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID do objetivo") @PathVariable Integer id);
}
