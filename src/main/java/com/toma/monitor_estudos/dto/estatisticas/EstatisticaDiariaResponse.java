package com.toma.monitor_estudos.dto.estatisticas;

import java.time.LocalDate;
import java.util.List;

public record EstatisticaDiariaResponse(
        LocalDate data,
        long tempoTotalMinutos,
        List<SessaoResumoResponse> sessoes
) {}
