package com.toma.monitor_estudos.dto.estatisticas;

import com.toma.monitor_estudos.domain.StatusSessao;
import java.time.LocalTime;

public record SessaoResumoResponse(
        Long sessaoId,
        Long materiaId,
        String materiaTitulo,
        String materiaCorHex,
        LocalTime horaInicio,
        LocalTime horaFim,
        long duracaoMinutos,
        StatusSessao status
) {}
