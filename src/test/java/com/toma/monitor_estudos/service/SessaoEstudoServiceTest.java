package com.toma.monitor_estudos.service;

import com.toma.monitor_estudos.domain.Materia;
import com.toma.monitor_estudos.domain.SessaoEstudo;
import com.toma.monitor_estudos.dto.SessaoEstudoRequest;
import com.toma.monitor_estudos.dto.SessaoEstudoResponse;
import com.toma.monitor_estudos.exception.SessaoInvalidaException;
import com.toma.monitor_estudos.repository.MateriaRepository;
import com.toma.monitor_estudos.repository.SessaoEstudoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

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
        // Arrange
        LocalDateTime inicio = LocalDateTime.of(2026, 7, 16, 15, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 7, 16, 14, 0);

        // Criamos o request com as datas inválidas (fim antes do início)
        SessaoEstudoRequest requestInvalido = new SessaoEstudoRequest(1L, inicio, fim);

        // Act & Assert
        Assertions.assertThrows(SessaoInvalidaException.class, () -> {
            sessaoEstudoService.salvar(requestInvalido);
        });
    }

    @Test
    void deveSalvarSessaoComSucessoQuandoDadosForemValidos() {
        // Arrange
        LocalDateTime inicio = LocalDateTime.of(2026, 7, 16, 14, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 7, 16, 15, 0);
        Long materiaId = 1L;

        SessaoEstudoRequest request = new SessaoEstudoRequest(materiaId, inicio, fim);

        Materia materia = new Materia();
        materia.setId(materiaId);
        materia.setTitulo("Estrutura de Dados");

        SessaoEstudo sessaoSalvaNoBanco = new SessaoEstudo();
        sessaoSalvaNoBanco.setId(10L);
        sessaoSalvaNoBanco.setDataInicio(inicio);
        sessaoSalvaNoBanco.setDataFim(fim);
        sessaoSalvaNoBanco.setMateria(materia); // Vincula a matéria para o mapeador do Service não lançar NullPointerException

        Mockito.when(materiaRepository.findById(materiaId))
                .thenReturn(Optional.of(materia));

        Mockito.when(sessaoEstudoRepository.save(any(SessaoEstudo.class)))
                .thenReturn(sessaoSalvaNoBanco);

        // Act
        SessaoEstudoResponse response = sessaoEstudoService.salvar(request);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(10L, response.id());
        Assertions.assertEquals(inicio, response.dataInicio());
        Assertions.assertEquals(fim, response.dataFim());
        Assertions.assertEquals(materiaId, response.materiaId());
        Assertions.assertEquals("Estrutura de Dados", response.materiaTitulo());
    }
}
