package com.toma.monitor_estudos.controller;

import com.toma.monitor_estudos.domain.SessaoEstudo;
import com.toma.monitor_estudos.dto.SessaoEstudoRequest;
import com.toma.monitor_estudos.dto.SessaoEstudoResponse;
import com.toma.monitor_estudos.service.SessaoEstudoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<SessaoEstudoResponse>> listar(){
        List<SessaoEstudoResponse> response = sessaoEstudoService.listarTodas()
                .stream()
                .map(sessao -> new SessaoEstudoResponse(
                        sessao.getId(),
                        sessao.getDataInicio(),
                        sessao.getDataFim(),
                        sessao.getMateria().getId(),
                        sessao.getMateria().getTitulo()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        sessaoEstudoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessaoEstudoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody SessaoEstudoRequest request){
        SessaoEstudo dadosNovos = new SessaoEstudo();
        dadosNovos.setDataInicio(request.dataInicio());
        dadosNovos.setDataFim(request.dataFim());

        SessaoEstudo sessaoAtualizada = sessaoEstudoService.atualizar(id, request.materiaId(), dadosNovos);

        SessaoEstudoResponse response = new SessaoEstudoResponse(
                sessaoAtualizada.getId(),
                sessaoAtualizada.getDataInicio(),
                sessaoAtualizada.getDataFim(),
                sessaoAtualizada.getMateria().getId(),
                sessaoAtualizada.getMateria().getTitulo()
        );

        return ResponseEntity.ok(response);
    }


}
