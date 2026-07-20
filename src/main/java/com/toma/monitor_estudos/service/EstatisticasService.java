package com.toma.monitor_estudos.service;

import com.toma.monitor_estudos.domain.SessaoEstudo;
import com.toma.monitor_estudos.domain.StatusSessao;
import com.toma.monitor_estudos.dto.estatisticas.EstatisticaDiariaResponse;
import com.toma.monitor_estudos.dto.estatisticas.SessaoResumoResponse;
import com.toma.monitor_estudos.repository.SessaoEstudoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class EstatisticasService {

    private final SessaoEstudoRepository sessaoEstudoRepository;

    public EstatisticasService(SessaoEstudoRepository sessaoEstudoRepository) {
        this.sessaoEstudoRepository = sessaoEstudoRepository;
    }

    @Transactional(readOnly = true)
    public EstatisticaDiariaResponse obterEstatisticaDiaria(LocalDate data) {
        // Define o intervalo exato do dia solicitado: 00:00:00 até 23:59:59
        LocalDateTime inicioDia = data.atStartOfDay();
        LocalDateTime fimDia = data.atTime(LocalTime.MAX);

        LocalDateTime agora = LocalDateTime.now();

        // Busca todas as sessões que iniciaram dentro dessa data
        List<SessaoEstudo> sessoes = sessaoEstudoRepository.findByDataInicioBetween(inicioDia, fimDia);

        // Mapeia cada sessão da entidade para o DTO de resumo
        List<SessaoResumoResponse> sessoesResumo = sessoes.stream()
                .map(sessao -> mapearParaResumo(sessao, agora))
                .toList();

        // Calcula a soma total de minutos de todas as sessões do dia
        long tempoTotalMinutos = sessoesResumo.stream()
                .mapToLong(SessaoResumoResponse::duracaoMinutos)
                .sum();

        return new EstatisticaDiariaResponse(data, tempoTotalMinutos, sessoesResumo);
    }

    private SessaoResumoResponse mapearParaResumo(SessaoEstudo sessao, LocalDateTime agora) {
        boolean emAndamento = sessao.getDataFim() == null;
        StatusSessao status = emAndamento ? StatusSessao.EM_ANDAMENTO : StatusSessao.FINALIZADA;

        LocalDateTime pontoFinalCalculo = emAndamento ? agora : sessao.getDataFim();

        long duracaoMinutos = Duration.between(sessao.getDataInicio(), pontoFinalCalculo).toMinutes();

        LocalTime horaFim = emAndamento ? null : sessao.getDataFim().toLocalTime();

        return new SessaoResumoResponse(
                sessao.getId(),
                sessao.getMateria().getId(),
                sessao.getMateria().getTitulo(),
                sessao.getMateria().getCor(),
                sessao.getDataInicio().toLocalTime(),
                horaFim,
                Math.max(0, duracaoMinutos), // Evita minutos negativos em casos atípicos
                status
        );
    }
}
