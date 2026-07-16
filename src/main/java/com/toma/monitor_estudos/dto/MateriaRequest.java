package com.toma.monitor_estudos.dto;

import jakarta.validation.constraints.NotBlank;

public record MateriaRequest(
        @NotBlank String titulo,
        String cor
) {}
