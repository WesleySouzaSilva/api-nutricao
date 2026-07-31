package br.com.nutricao.controller.documentacao;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.nutricao.domain.dto.insercao.AlimentoFavoritoRequest;
import br.com.nutricao.domain.dto.visualizacao.AlimentoFavoritoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Alimentos Favoritos")
public interface AlimentoFavoritoControllerSwagger {

    @Operation(summary = "Adicionar alimento aos favoritos", description = "Marca um alimento como favorito para o usuario informado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alimento adicionado aos favoritos com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos — usuario ou alimento inexistente, ou favorito ja cadastrado")
    })
    ResponseEntity<AlimentoFavoritoResponse> adicionar(@RequestBody AlimentoFavoritoRequest request);

    @Operation(summary = "Listar favoritos do usuario", description = "Retorna a lista de alimentos marcados como favoritos por um usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de favoritos retornada com sucesso")
    })
    ResponseEntity<List<AlimentoFavoritoResponse>> buscarPorUsuario(
            @Parameter(description = "ID do usuario") @PathVariable Integer usuarioId);

    @Operation(summary = "Remover alimento dos favoritos", description = "Remove um alimento da lista de favoritos do usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Favorito removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Favorito nao encontrado para o usuario e alimento informados")
    })
    ResponseEntity<Void> remover(
            @Parameter(description = "ID do usuario") @PathVariable Integer usuarioId,
            @Parameter(description = "ID do alimento") @PathVariable Integer alimentoId);
}
