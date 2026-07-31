package br.com.nutricao.controller.documentacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.nutricao.domain.dto.filtro.RegistroDiarioCamposFiltro;
import br.com.nutricao.domain.dto.insercao.RegistroDiarioRequest;
import br.com.nutricao.domain.dto.visualizacao.RegistroDiarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Registros Diarios")
public interface RegistroDiarioControllerSwagger {

    @Operation(summary = "Criar novo registro diario", description = "Registra o consumo diario de calorias e macronutrientes (proteinas, carboidratos, gorduras) para um usuario em uma data especifica")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro diario criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos — usuario inexistente ou campos obrigatorios ausentes")
    })
    ResponseEntity<RegistroDiarioResponse> criar(@RequestBody RegistroDiarioRequest request);

    @Operation(summary = "Listar registros diarios", description = "Retorna a lista paginada de registros diarios com suporte a filtros por usuario e periodo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de registros diarios retornada com sucesso")
    })
    ResponseEntity<Page<RegistroDiarioResponse>> listar(
            @Parameter(description = "Filtros: usuarioId (integer), dataInicio (date), dataFim (date)") RegistroDiarioCamposFiltro filtro,
            @Parameter(description = "Parametros de paginacao (page, size, sort)") Pageable pageable);

    @Operation(summary = "Buscar registro diario por ID", description = "Retorna os dados de um registro diario especifico pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro diario encontrado"),
            @ApiResponse(responseCode = "404", description = "Registro diario nao encontrado para o ID informado")
    })
    ResponseEntity<RegistroDiarioResponse> buscarPorId(
            @Parameter(description = "ID do registro diario") @PathVariable Integer id);

    @Operation(summary = "Atualizar registro diario", description = "Atualiza os valores de consumo de um registro diario existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro diario atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro diario nao encontrado para o ID informado"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos")
    })
    ResponseEntity<RegistroDiarioResponse> atualizar(
            @Parameter(description = "ID do registro diario") @PathVariable Integer id,
            @RequestBody RegistroDiarioRequest request);

    @Operation(summary = "Remover registro diario", description = "Remove permanentemente um registro diario da base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro diario removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro diario nao encontrado para o ID informado")
    })
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID do registro diario") @PathVariable Integer id);
}
