package com.toma.monitor_estudos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MateriaResponse(
        @Schema(description = "Identificador da matéria", example = "3")
        Long id,
        @Schema(description = "Título ou nome da matéria de estudo", example = "Estrutura de Dados")
        String titulo,

        @Schema(description = "Cor em hexadecimal utilizada para exibição em gráficos", example = "#3357FF")
        String cor
) {}
