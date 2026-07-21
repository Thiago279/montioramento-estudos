package com.toma.monitor_estudos.controller;

import com.toma.monitor_estudos.dto.estatisticas.EstatisticaDiariaResponse;
import com.toma.monitor_estudos.dto.estatisticas.EstatisticaSemanalResponse;
import com.toma.monitor_estudos.service.EstatisticasService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/monitor-estudos/estatisticas")
public class EstatisticasController {

    private final EstatisticasService estatisticasService;

    public EstatisticasController(EstatisticasService estatisticasService) {
        this.estatisticasService = estatisticasService;
    }

    @GetMapping("/diaria")
    public ResponseEntity<EstatisticaDiariaResponse> obterDiaria(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        // Se o frontend não enviar o parâmetro ?data=YYYY-MM-DD, assume a data de hoje por padrão
        LocalDate dataConsulta = (data != null) ? data : LocalDate.now();

        EstatisticaDiariaResponse response = estatisticasService.obterEstatisticaDiaria(dataConsulta);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/semanal")
    public ResponseEntity<EstatisticaSemanalResponse> obterSemanal(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data){
        EstatisticaSemanalResponse response = estatisticasService.obterEstatisticaSemanal(data);
        return ResponseEntity.ok(response);
    }

}
