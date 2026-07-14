package com.toma.monitor_estudos.service;

import com.toma.monitor_estudos.domain.Materia;
import com.toma.monitor_estudos.repository.MateriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaService {

    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository){
        this.materiaRepository = materiaRepository;
    }

    public Materia salvar(Materia materia){
        return materiaRepository.save(materia);
    }

    public List<Materia> listarTodas(){
        return materiaRepository.findAll();
    }

    public Materia buscarPorId(Long id){
        return materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matéria não encontrada com ID: "+ id));
    }
}
