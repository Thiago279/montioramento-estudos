package com.toma.monitor_estudos.dto;

import java.time.LocalDateTime;

public record SessaoEstudoRequest(
        Long materiaId,
        LocalDateTime dataInicio,
        LocalDateTime dataFim
) {}
