package com.toma.monitor_estudos.controller;

import com.toma.monitor_estudos.domain.Materia;
import com.toma.monitor_estudos.service.MateriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {
    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService){
        this.materiaService = materiaService;
    }

    @PostMapping
    public ResponseEntity<Materia> criar(@RequestBody Materia materia){
        Materia novaMateria = materiaService.salvar(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMateria);
    }

    @GetMapping
    public ResponseEntity<List<Materia>> listar(){
        List<Materia> materias = materiaService.listarTodas();
        return ResponseEntity.ok(materias);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        materiaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
