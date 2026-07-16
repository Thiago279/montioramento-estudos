package com.toma.monitor_estudos.controller;

import com.toma.monitor_estudos.dto.SessaoEstudoRequest;
import com.toma.monitor_estudos.dto.SessaoEstudoResponse;
import com.toma.monitor_estudos.service.SessaoEstudoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitor-estudos/sessoes")
public class SessaoEstudoController {

    private final SessaoEstudoService sessaoEstudoService;

    public SessaoEstudoController(SessaoEstudoService sessaoEstudoService) {
        this.sessaoEstudoService = sessaoEstudoService;
    }

    @PostMapping
    public ResponseEntity<SessaoEstudoResponse> criarSessao(@RequestBody @Valid SessaoEstudoRequest request) {
        SessaoEstudoResponse response = sessaoEstudoService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SessaoEstudoResponse>> listar() {
        List<SessaoEstudoResponse> response = sessaoEstudoService.listarTodas();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        sessaoEstudoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessaoEstudoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid SessaoEstudoRequest request) {
        SessaoEstudoResponse response = sessaoEstudoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }
}
