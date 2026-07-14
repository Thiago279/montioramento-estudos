package com.toma.monitor_estudos.dto;

import java.time.LocalDateTime;

public record SessaoEstudoResponse(
        Long id,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        Long materiaId,
        String materiaTitulo
) {}
