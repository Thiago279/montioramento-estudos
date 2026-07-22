package com.toma.monitor_estudos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MateriaRequest(
        @Schema(description = "Título ou nome da matéria de estudo", example = "Estrutura de Dados")
        @NotBlank String titulo,

        @Schema(description = "Cor em hexadecimal utilizada para exibição em gráficos", example = "#3357FF")
        String cor
) {}
