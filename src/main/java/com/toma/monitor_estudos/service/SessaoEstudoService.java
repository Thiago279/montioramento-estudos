package com.toma.monitor_estudos.service;

import com.toma.monitor_estudos.domain.Materia;
import com.toma.monitor_estudos.domain.SessaoEstudo;
import com.toma.monitor_estudos.dto.SessaoEstudoRequest;
import com.toma.monitor_estudos.dto.SessaoEstudoResponse;
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
    public SessaoEstudoResponse salvar(SessaoEstudoRequest request) {
        validarIntervaloDatas(request.dataInicio(), request.dataFim());

        Materia materia = materiaRepository.findById(request.materiaId())
                .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada com o ID: " + request.materiaId()));

        SessaoEstudo sessao = new SessaoEstudo();
        sessao.setDataInicio(request.dataInicio());
        sessao.setDataFim(request.dataFim());
        sessao.setMateria(materia);

        SessaoEstudo sessaoSalva = sessaoEstudoRepository.save(sessao);
        return mapearParaResponse(sessaoSalva);
    }

    public List<SessaoEstudoResponse> listarTodas() {
        return sessaoEstudoRepository.findAll()
                .stream()
                .map(this::mapearParaResponse)
                .toList();
    }

    @Transactional
    public void deletar(Long id) {
        if (!sessaoEstudoRepository.existsById(id)) {
            throw new EntityNotFoundException("Sessão de estudos não encontrada");
        }
        sessaoEstudoRepository.deleteById(id);
    }

    @Transactional
    public SessaoEstudoResponse atualizar(Long id, SessaoEstudoRequest request) {
        SessaoEstudo sessaoExistente = sessaoEstudoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada com o ID: " + id));

        validarIntervaloDatas(request.dataInicio(), request.dataFim());

        Materia materia = materiaRepository.findById(request.materiaId())
                .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada com o ID: " + request.materiaId()));

        sessaoExistente.setMateria(materia);
        sessaoExistente.setDataInicio(request.dataInicio());
        sessaoExistente.setDataFim(request.dataFim());

        SessaoEstudo sessaoAtualizada = sessaoEstudoRepository.save(sessaoExistente);
        return mapearParaResponse(sessaoAtualizada);
    }

    private void validarIntervaloDatas(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null) {
            throw new SessaoInvalidaException("A data de início é obrigatória.");
        }
        if (fim != null && inicio.isAfter(fim)) {
            throw new SessaoInvalidaException("A data de início não pode ser posterior à data de fim.");
        }
    }

    private SessaoEstudoResponse mapearParaResponse(SessaoEstudo sessao) {
        return new SessaoEstudoResponse(
                sessao.getId(),
                sessao.getDataInicio(),
                sessao.getDataFim(),
                sessao.getMateria().getId(),
                sessao.getMateria().getTitulo()
        );
    }
}
