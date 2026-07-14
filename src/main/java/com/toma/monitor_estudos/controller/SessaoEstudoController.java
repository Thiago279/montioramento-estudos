package com.toma.monitor_estudos.controller;

import com.toma.monitor_estudos.domain.SessaoEstudo;
import com.toma.monitor_estudos.dto.SessaoEstudoRequest;
import com.toma.monitor_estudos.dto.SessaoEstudoResponse;
import com.toma.monitor_estudos.service.SessaoEstudoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor-estudos/sessoes")
public class SessaoEstudoController {

    private final SessaoEstudoService sessaoEstudoService;

    public SessaoEstudoController(SessaoEstudoService sessaoEstudoService){
        this.sessaoEstudoService = sessaoEstudoService;
    }

    @PostMapping
    public ResponseEntity<SessaoEstudoResponse> criarSessao(@RequestBody SessaoEstudoRequest request) {
        SessaoEstudo sessao = new SessaoEstudo();
        sessao.setDataInicio(request.dataInicio());
        sessao.setDataFim(request.dataFim());

        SessaoEstudo novaSessao = sessaoEstudoService.salvar(request.materiaId(), sessao);
        SessaoEstudoResponse response = new SessaoEstudoResponse(
                novaSessao.getId(),
                novaSessao.getDataInicio(),
                novaSessao.getDataFim(),
                novaSessao.getMateria().getId(),
                novaSessao.getMateria().getTitulo()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
