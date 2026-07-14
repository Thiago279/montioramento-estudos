package com.toma.monitor_estudos.controller;

import com.toma.monitor_estudos.domain.Materia;
import com.toma.monitor_estudos.dto.MateriaRequest;
import com.toma.monitor_estudos.dto.MateriaResponse;
import com.toma.monitor_estudos.service.MateriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitor-estudos/materias")
public class MateriaController {
    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService){
        this.materiaService = materiaService;
    }

    @PostMapping
    public ResponseEntity<MateriaResponse> criar(@RequestBody MateriaRequest request){
        Materia materia = new Materia(request.titulo());
        Materia materiaSalva = materiaService.salvar(materia);
        MateriaResponse response = new MateriaResponse(materiaSalva.getId(), materiaSalva.getTitulo());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MateriaResponse>> listar(){
        List<MateriaResponse> materias = materiaService.listarTodas()
                .stream()
                .map(materia -> new MateriaResponse(
                        materia.getId(),
                        materia.getTitulo()
                ))
                .toList();
        return ResponseEntity.ok(materias);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        materiaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
