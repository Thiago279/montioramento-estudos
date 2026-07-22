package com.toma.monitor_estudos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record SessaoEstudoRequest(

        @Schema(description = "ID da matéria associada à sessão de estudo", example = "1")
        @NotNull(message = "O ID da matéria é obrigatório")
        Long materiaId,

        @Schema(
                description = "Data e horário de início da sessão (padrão ISO 8601: YYYY-MM-DDTHH:mm:ss)",
                example = "2026-07-22T14:30:00"
        )
        @NotNull(message = "A data de início é obrigatória")
        LocalDateTime dataInicio,

        @Schema(
                description = "Data e horário de término da sessão (padrão ISO 8601: YYYY-MM-DDTHH:mm:ss)",
                example = "2026-07-22T16:00:00"
        )
        LocalDateTime dataFim
) {}
