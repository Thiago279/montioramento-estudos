package com.toma.monitor_estudos.dto.estatisticas;

import java.time.LocalDate;
import java.util.List;

public record DiaSemanaResponse(
        LocalDate data,
        String diaSemana, // Ex: "SEGUNDA-FEIRA"
        long tempoTotalMinutos,
        List<MateriaTempoResponse> materias
) {

}
