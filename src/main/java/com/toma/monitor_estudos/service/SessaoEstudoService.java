package com.toma.monitor_estudos.service;

import com.toma.monitor_estudos.domain.Materia;
import com.toma.monitor_estudos.domain.SessaoEstudo;
import com.toma.monitor_estudos.dto.SessaoEstudoRequest;
import com.toma.monitor_estudos.dto.SessaoEstudoResponse;
import com.toma.monitor_estudos.exception.SessaoEmAndamentoException;
import com.toma.monitor_estudos.exception.SessaoInvalidaException;
import com.toma.monitor_estudos.exception.SessaoJaFinalizadaException;
import com.toma.monitor_estudos.repository.MateriaRepository;
import com.toma.monitor_estudos.repository.SessaoEstudoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

        validarSessaoEmAndamento(request);
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
        validarSessaoEmAndamento(request);
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

    @Transactional
    public SessaoEstudoResponse finalizar(Long id){
        SessaoEstudo sessao = sessaoEstudoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada com o ID: " + id));

        if(sessao.getDataFim() != null){
            throw new SessaoJaFinalizadaException("Esta sessão de estudos já foi finalizada.");
        }
        sessao.setDataFim(LocalDateTime.now());
        SessaoEstudo sessaoSalva = sessaoEstudoRepository.save(sessao);

        return mapearParaResponse(sessaoSalva);
    }


    private void validarIntervaloDatas(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null) {
            throw new SessaoInvalidaException("A data de início é obrigatória.");
        } else if (fim != null && inicio.isAfter(fim)) {
            throw new SessaoInvalidaException("A data de início não pode ser posterior à data de fim.");
        }
    }

    private void validarSessaoEmAndamento(SessaoEstudoRequest request){
        Optional<SessaoEstudo> sessaoEmAndamentoOpt = sessaoEstudoRepository.findByDataFimIsNull();
        if(sessaoEmAndamentoOpt.isPresent()){
            SessaoEstudo sessaoEmAndamento = sessaoEmAndamentoOpt.get();
            LocalDateTime inicioEmAndamento = sessaoEmAndamento.getDataInicio();

            if (request.dataFim() == null) {
                throw new SessaoEmAndamentoException("Já existe uma sessão de estudos em andamento. Finalize-a antes de iniciar outra.");
            }

            boolean inicioConflita = !request.dataInicio().isBefore(inicioEmAndamento);
            boolean fimConflita = request.dataFim().isAfter(inicioEmAndamento);

            if (inicioConflita || fimConflita) {
                throw new SessaoEmAndamentoException("Não é possível registrar uma sessão em período conflitante com a sessão atualmente em andamento.");
            }
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
