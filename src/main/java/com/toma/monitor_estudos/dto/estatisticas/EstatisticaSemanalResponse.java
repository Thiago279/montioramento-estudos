package com.toma.monitor_estudos.dto.estatisticas;

import java.time.LocalDate;
import java.util.List;

public record EstatisticaSemanalResponse(
        LocalDate dataInicio,
        LocalDate dataFim,
        long tempoTotalMinutos,
        List<DiaSemanaResponse> dias
) {
}
