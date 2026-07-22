package com.toma.monitor_estudos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record SessaoEstudoResponse(
        @Schema(description = "Identificador da sessao de estudo", example = "3")
        Long id,
        @Schema(
                description = "Data e horário de início da sessão (padrão ISO 8601: YYYY-MM-DDTHH:mm:ss)",
                example = "2026-07-22T14:30:00"
        )
        LocalDateTime dataInicio,
        @Schema(
                description = "Data e horário de término da sessão (padrão ISO 8601: YYYY-MM-DDTHH:mm:ss)",
                example = "2026-07-22T16:00:00"
        )
        LocalDateTime dataFim,
        @Schema(description = "Identificador da matéria", example = "3")
        Long materiaId,
        @Schema(description = "Título ou nome da matéria de estudo", example = "Estrutura de Dados")
        String materiaTitulo
) {}
