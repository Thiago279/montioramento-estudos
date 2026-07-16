package com.toma.monitor_estudos.service;

import com.toma.monitor_estudos.domain.Materia;
import com.toma.monitor_estudos.dto.MateriaRequest;
import com.toma.monitor_estudos.dto.MateriaResponse;
import com.toma.monitor_estudos.repository.MateriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MateriaServiceTest {

    @Mock
    private MateriaRepository materiaRepository;

    @InjectMocks
    private MateriaService materiaService;

    @Test
    void deveSalvarMateriaComSucessoComCorPersonalizada() {
        MateriaRequest request = new MateriaRequest("Cybersecurity", "#4A90E2");

        Materia materiaSalva = new Materia();
        materiaSalva.setId(1L);
        materiaSalva.setTitulo(request.titulo());
        materiaSalva.setCor(request.cor());

        Mockito.when(materiaRepository.save(any(Materia.class))).thenReturn(materiaSalva);

        MateriaResponse response = materiaService.salvar(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L, response.id());
        Assertions.assertEquals("Cybersecurity", response.titulo());
        Assertions.assertEquals("#4A90E2", response.cor());
    }

    @Test
    void deveSalvarMateriaComSucessoComCorPadraoQuandoCorForNula() {
        MateriaRequest request = new MateriaRequest("Algoritmos", null);

        Materia materiaSalva = new Materia();
        materiaSalva.setId(2L);
        materiaSalva.setTitulo(request.titulo());
        materiaSalva.setCor("#808080");

        Mockito.when(materiaRepository.save(any(Materia.class))).thenReturn(materiaSalva);

        MateriaResponse response = materiaService.salvar(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("#808080", response.cor()); // Garante que a cor padrão foi aplicada
    }

    @Test
    void deveLancarEntityNotFoundExceptionAoDeletarIdInexistente() {
        // Arrange
        Long idInexistente = 99L;
        Mockito.when(materiaRepository.existsById(idInexistente)).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            materiaService.deletar(idInexistente);
        });

        // Garante que o metodo deleteById NUNCA foi chamado já que o ID não existe
        Mockito.verify(materiaRepository, Mockito.never()).deleteById(any());
    }
}
