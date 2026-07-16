package com.toma.monitor_estudos.service;

import com.toma.monitor_estudos.domain.Materia;
import com.toma.monitor_estudos.domain.SessaoEstudo;
import com.toma.monitor_estudos.exception.SessaoInvalidaException;
import com.toma.monitor_estudos.repository.MateriaRepository;
import com.toma.monitor_estudos.repository.SessaoEstudoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class SessaoEstudoServiceTest {

    @Mock
    private SessaoEstudoRepository sessaoEstudoRepository;

    @Mock
    private MateriaRepository materiaRepository;

    @InjectMocks
    private SessaoEstudoService sessaoEstudoService;

    @Test
    void deveLancarExcecaoQuandoDatasForemInvalidas() {

        LocalDateTime inicio = LocalDateTime.of(2026, 7, 16, 15, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 7, 16, 14, 0);

        SessaoEstudo sessaoInvalida = new SessaoEstudo();
        sessaoInvalida.setDataInicio(inicio);
        sessaoInvalida.setDataFim(fim);

        Assertions.assertThrows(SessaoInvalidaException.class, () -> {
            sessaoEstudoService.salvar(1L, sessaoInvalida);
        });
    }
    @Test
    void deveSalvarSessaoComSucessoQuandoDadosForemValidos() {
        LocalDateTime inicio = LocalDateTime.of(2026, 7, 16, 14, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 7, 16, 15, 0);

        Materia materia = new Materia("Estrutura de Dados");
        materia.setId(1L);

        SessaoEstudo sessaoParaSalvar = new SessaoEstudo();
        sessaoParaSalvar.setDataInicio(inicio);
        sessaoParaSalvar.setDataFim(fim);

        org.mockito.Mockito.when(materiaRepository.findById(1L))
                .thenReturn(java.util.Optional.of(materia));

        org.mockito.Mockito.when(sessaoEstudoRepository.save(org.mockito.ArgumentMatchers.any(SessaoEstudo.class)))
                .thenReturn(sessaoParaSalvar);

        SessaoEstudo sessaoSalva = sessaoEstudoService.salvar(1L, sessaoParaSalvar);

        Assertions.assertNotNull(sessaoSalva);
        Assertions.assertEquals(inicio, sessaoSalva.getDataInicio());
        Assertions.assertEquals(fim, sessaoSalva.getDataFim());
        Assertions.assertEquals(materia, sessaoSalva.getMateria());
    }
}
