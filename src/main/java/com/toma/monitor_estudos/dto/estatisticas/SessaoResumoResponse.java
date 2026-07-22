package com.toma.monitor_estudos.dto.estatisticas;

import com.toma.monitor_estudos.domain.StatusSessao;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

public record SessaoResumoResponse(
        @Schema(description = "Identificador da sessao", example = "2")
        Long sessaoId,
        @Schema(description = "Identificador da matéria", example = "3")
        Long materiaId,
        @Schema(description = "Título ou nome da matéria de estudo", example = "Estrutura de Dados")
        String materiaTitulo,
        @Schema(description = "Cor em hexadecimal utilizada para exibição em gráficos", example = "#3357FF")
        String materiaCorHex,
        @Schema(
                description = " horário de início da sessão (padrão = HH:mm:ss)",
                example = "14:30:00"
        )
        LocalTime horaInicio,
        @Schema(
                description = " horário do fim da sessão (padrão = HH:mm:ss)",
                example = "16:30:00"
        )
        LocalTime horaFim,
        @Schema(description = "Tempo total (em minutos) estudando na sessão especificada", example = "120")
        long duracaoMinutos,
        @Schema(description = "status da sessão( Finalizada ou em andamento", example = "EM_ANDAMENTO")
        StatusSessao status
) {}
