package com.toma.monitor_estudos.service;

import com.toma.monitor_estudos.domain.Materia;
import com.toma.monitor_estudos.domain.SessaoEstudo;
import com.toma.monitor_estudos.exception.SessaoInvalidaException;
import com.toma.monitor_estudos.repository.MateriaRepository;
import com.toma.monitor_estudos.repository.SessaoEstudoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessaoEstudoService {

    private final SessaoEstudoRepository sessaoEstudoRepository;
    private final MateriaRepository materiaRepository;

    public SessaoEstudoService(SessaoEstudoRepository sessaoEstudoRepository, MateriaRepository materiaRepository) {
        this.sessaoEstudoRepository = sessaoEstudoRepository;
        this.materiaRepository = materiaRepository;
    }

    @Transactional
    public SessaoEstudo salvar(Long materiaId, SessaoEstudo sessao) {
        validarIntervaloDatas(sessao.getDataInicio(),sessao.getDataFim());
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada com o ID: " + materiaId));

        sessao.setMateria(materia);
        return sessaoEstudoRepository.save(sessao);
    }

    public List<SessaoEstudo> listarTodas(){
        return sessaoEstudoRepository.findAll();
    }

    public void deletar (Long id){
        if (!sessaoEstudoRepository.existsById(id)){
            throw new EntityNotFoundException("Sessão de estudos não encontrada");
        }
        sessaoEstudoRepository.deleteById(id);
    }

    public SessaoEstudo atualizar(Long id, Long novaMateriaId, SessaoEstudo dadosAtualizados){
        SessaoEstudo sessaoExistente = sessaoEstudoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada com o ID: " + id));
        validarIntervaloDatas(dadosAtualizados.getDataInicio(), dadosAtualizados.getDataFim());
        // 2. Busca a nova matéria (se foi alterada)
        Materia materia = materiaRepository.findById(novaMateriaId)
                .orElseThrow(() -> new RuntimeException("Matéria não encontrada com o ID: " + novaMateriaId));

        // 3. Atualiza os dados
        sessaoExistente.setMateria(materia);
        sessaoExistente.setDataInicio(dadosAtualizados.getDataInicio());
        sessaoExistente.setDataFim(dadosAtualizados.getDataFim());

        // 4. Salva no banco
        return sessaoEstudoRepository.save(sessaoExistente);
    }

    private void validarIntervaloDatas(LocalDateTime inicio, LocalDateTime fim) {
        if(inicio == null) {
            throw new SessaoInvalidaException("A data de início é obrigatória.");
        }
        if(fim != null && inicio.isAfter(fim)){
            throw new SessaoInvalidaException("A data de início não pode ser posterior à data de fim.");
        }
    }

}
