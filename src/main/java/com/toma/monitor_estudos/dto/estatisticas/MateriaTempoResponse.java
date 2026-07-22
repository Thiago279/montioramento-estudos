package com.toma.monitor_estudos.dto.estatisticas;

import io.swagger.v3.oas.annotations.media.Schema;

public record MateriaTempoResponse(
        @Schema(description = "Identificador da matéria", example = "3")
        Long materiaId,
        @Schema(description = "Título ou nome da matéria de estudo", example = "Estrutura de Dados")
        String materiaTitulo,
        @Schema(description = "Tempo total estudando a matéria em minutos", example = "180")
        long tempoAcumuladoMinutos,
        @Schema(description = "Cor em hexadecimal utilizada para exibição em gráficos", example = "#3357FF")
        String materiaCorHex
) {
}
