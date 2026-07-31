package br.com.nutricao.controller.documentacao;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.nutricao.domain.dto.insercao.UsuarioRequest;
import br.com.nutricao.domain.dto.visualizacao.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuarios")
public interface UsuarioControllerSwagger {

    @Operation(summary = "Cadastrar novo usuario", description = "Cria um novo usuario na base de dados. O email deve ser unico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos — email duplicado, campos obrigatorios ausentes ou formato invalido")
    })
    ResponseEntity<UsuarioResponse> criar(@RequestBody UsuarioRequest request);

    @Operation(summary = "Listar todos os usuarios", description = "Retorna a lista completa de usuarios cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuarios retornada com sucesso")
    })
    ResponseEntity<List<UsuarioResponse>> listar();

    @Operation(summary = "Buscar usuario por ID", description = "Retorna os dados de um usuario especifico pelo seu identificador unico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado para o ID informado")
    })
    ResponseEntity<UsuarioResponse> buscarPorId(
            @Parameter(description = "ID do usuario") @PathVariable Integer id);

    @Operation(summary = "Buscar usuario por email", description = "Retorna os dados de um usuario pelo endereco de email cadastrado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado para o email informado")
    })
    ResponseEntity<UsuarioResponse> buscarPorEmail(
            @Parameter(description = "Email do usuario") @PathVariable String email);

    @Operation(summary = "Atualizar usuario", description = "Atualiza os dados cadastrais de um usuario existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado para o ID informado"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos — email duplicado ou formato invalido")
    })
    ResponseEntity<UsuarioResponse> atualizar(
            @Parameter(description = "ID do usuario") @PathVariable Integer id,
            @RequestBody UsuarioRequest request);

    @Operation(summary = "Excluir usuario", description = "Remove permanentemente um usuario da base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado para o ID informado")
    })
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID do usuario") @PathVariable Integer id);
}
