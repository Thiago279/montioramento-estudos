package com.toma.monitor_estudos.dto.erro;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ErroResponse(
        @Schema(description = "Timestamp de quando o erro ocorreu", example = "2026-07-22T15:30:00")
        LocalDateTime timestamp,

        @Schema(description = "Código de status HTTP", example = "400")
        Integer status,

        @Schema(description = "Descrição curta do status HTTP", example = "Bad Request")
        String error,

        @Schema(description = "Mensagem detalhada do erro ou falha de validação", example = "O campo 'titulo' não pode estar em branco")
        String message,

        @Schema(description = "Rota/Endpoint onde ocorreu a exceção", example = "/monitor-estudos/materias")
        String path
) {}
