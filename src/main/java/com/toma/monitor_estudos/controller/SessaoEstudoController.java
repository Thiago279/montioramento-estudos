package com.toma.monitor_estudos.controller;

import com.toma.monitor_estudos.dto.SessaoEstudoRequest;
import com.toma.monitor_estudos.dto.SessaoEstudoResponse;
import com.toma.monitor_estudos.dto.erro.ErroResponse;
import com.toma.monitor_estudos.service.SessaoEstudoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitor-estudos/sessoes")
@Tag(name = "Sessões de Estudo", description = "Endpoints para registro, acompanhamento e finalização de sessões de estudo")
public class SessaoEstudoController {

    private final SessaoEstudoService sessaoEstudoService;

    public SessaoEstudoController(SessaoEstudoService sessaoEstudoService) {
        this.sessaoEstudoService = sessaoEstudoService;
    }

    @PostMapping
    @Operation(summary = "Registra/inicia uma sessão de estudo", description = "Cria um novo registro de sessão associado a uma matéria válida.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Sessão registrada com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos ou período de sessão inconsistente (data início > data fim)",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))
    ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Matéria associada não foi encontrada",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))
    )
    })
    public ResponseEntity<SessaoEstudoResponse> criarSessao(@RequestBody @Valid SessaoEstudoRequest request) {
        SessaoEstudoResponse response = sessaoEstudoService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Lista histórico de sessões", description = "Retorna uma lista com todas as sessões de estudo cadastradas")
    @ApiResponse(responseCode = "200", description = "Histórico retornado com sucesso")
    public ResponseEntity<List<SessaoEstudoResponse>> listar() {
        List<SessaoEstudoResponse> response = sessaoEstudoService.listarTodas();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma sessão de estudo", description = "Exclui uma sessão de estudo cadastrada pelo seu ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Sessão de estudo excluída com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Sessão de estudo não encontrada para exclusão",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))
    )
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        sessaoEstudoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma sessão de estudo", description = "Atualiza os dados de uma sessão de estudo existente pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sessão de estudo atualizada com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))
    ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Sessão de estudo ou Matéria não foi encontrada",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))
    )
    })
    public ResponseEntity<SessaoEstudoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid SessaoEstudoRequest request) {
        SessaoEstudoResponse response = sessaoEstudoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }
}
