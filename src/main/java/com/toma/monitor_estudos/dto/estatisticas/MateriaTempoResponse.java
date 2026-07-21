package com.toma.monitor_estudos.dto.estatisticas;

public record MateriaTempoResponse(
        Long materiaId,
        String materiaTitulo,
        long tempoAcumuladoMinutos,
        String materiaCorHex
) {
}
