package com.catalogo.catalogo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.catalogo.catalogo.dto.UnidadMedidaRequestDTO;
import com.catalogo.catalogo.dto.UnidadMedidaResponseDTO;
import com.catalogo.catalogo.exception.UnidadMedidaNotFoundException;
import com.catalogo.catalogo.model.UnidadMedida;
import com.catalogo.catalogo.repository.UnidadMedidaRespository;

@ExtendWith(MockitoExtension.class)
class UnidadMedidaServiceTest {

    @Mock
    private UnidadMedidaRespository unidadMedidaRepository;

    @InjectMocks
    private UnidadMedidaService unidadMedidaService;

    private UnidadMedida buildUnidadMedida() {
        UnidadMedida um = new UnidadMedida();
        um.setId(1L);
        um.setName("Kilogramo");
        um.setAbreviatura("kg");
        um.setActivo(true);
        return um;
    }

    @Test
    void createUnidadMedida_ShouldReturnResponseDTO() {
        UnidadMedidaRequestDTO request = new UnidadMedidaRequestDTO("Kilogramo", "kg");
        UnidadMedida saved = buildUnidadMedida();

        when(unidadMedidaRepository.save(any(UnidadMedida.class))).thenReturn(saved);

        UnidadMedidaResponseDTO result = unidadMedidaService.createUnidadMedida(request);

        assertNotNull(result);
        assertEquals("Kilogramo", result.nombre());
        assertEquals("kg", result.abreviatura());
    }

    @Test
    void searchUnidadMedidaByAbreviatura_ShouldReturnResponseDTO() {
        UnidadMedida um = buildUnidadMedida();

        when(unidadMedidaRepository.findByAbreviatura("kg")).thenReturn(Optional.of(um));

        UnidadMedidaResponseDTO result = unidadMedidaService.searchUnidadMedidaByAbreviatura("kg");

        assertNotNull(result);
        assertEquals("kg", result.abreviatura());
    }

    @Test
    void searchUnidadMedidaByAbreviatura_ShouldThrowNotFoundException() {
        when(unidadMedidaRepository.findByAbreviatura("INVALID")).thenReturn(Optional.empty());

        assertThrows(UnidadMedidaNotFoundException.class,
                () -> unidadMedidaService.searchUnidadMedidaByAbreviatura("INVALID"));
    }

    @Test
    void searchUnidadMedidaById_ShouldReturnResponseDTO() {
        UnidadMedida um = buildUnidadMedida();

        when(unidadMedidaRepository.findById(1L)).thenReturn(Optional.of(um));

        UnidadMedidaResponseDTO result = unidadMedidaService.searchUnidadMedidaById(1L);

        assertNotNull(result);
        assertEquals("Kilogramo", result.nombre());
    }

    @Test
    void searchUnidadMedidaById_ShouldThrowNotFoundException() {
        when(unidadMedidaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UnidadMedidaNotFoundException.class, () -> unidadMedidaService.searchUnidadMedidaById(99L));
    }

    @Test
    void searchAllUnidadesMedida_ShouldReturnList() {
        UnidadMedida um1 = buildUnidadMedida();
        UnidadMedida um2 = new UnidadMedida();
        um2.setId(2L);
        um2.setName("Metro");
        um2.setAbreviatura("m");
        um2.setActivo(true);

        when(unidadMedidaRepository.findAll()).thenReturn(List.of(um1, um2));

        List<UnidadMedidaResponseDTO> result = unidadMedidaService.searchAllUnidadesMedida();

        assertEquals(2, result.size());
        assertEquals("Kilogramo", result.get(0).nombre());
        assertEquals("Metro", result.get(1).nombre());
    }

    @Test
    void updateUnidadMedida_ShouldReturnUpdatedDTO() {
        UnidadMedida existing = buildUnidadMedida();
        UnidadMedidaRequestDTO request = new UnidadMedidaRequestDTO("Libra", "lb");

        when(unidadMedidaRepository.findById(1L)).thenReturn(Optional.of(existing));

        UnidadMedidaResponseDTO result = unidadMedidaService.updateUnidadMedida(1L, request);

        assertNotNull(result);
        assertEquals("Libra", result.nombre());
        assertEquals("lb", result.abreviatura());
    }

    @Test
    void updateUnidadMedida_ShouldThrowNotFoundException() {
        UnidadMedidaRequestDTO request = new UnidadMedidaRequestDTO("Test", "t");

        when(unidadMedidaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UnidadMedidaNotFoundException.class,
                () -> unidadMedidaService.updateUnidadMedida(99L, request));
    }

    @Test
    void deleteUnidadMedida_ShouldSetActivoFalse() {
        UnidadMedida um = buildUnidadMedida();

        when(unidadMedidaRepository.findById(1L)).thenReturn(Optional.of(um));

        unidadMedidaService.deleteUnidadMedida(1L);

        assertFalse(um.isActivo());
    }

    @Test
    void deleteUnidadMedida_ShouldThrowNotFoundException() {
        when(unidadMedidaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UnidadMedidaNotFoundException.class, () -> unidadMedidaService.deleteUnidadMedida(99L));
    }

    @Test
    void deleteUnidadMedidaByAbreviatura_ShouldSetActivoFalse() {
        UnidadMedida um = buildUnidadMedida();

        when(unidadMedidaRepository.findByAbreviatura("kg")).thenReturn(Optional.of(um));

        unidadMedidaService.deleteUnidadMedidaByAbreviatura("kg");

        assertFalse(um.isActivo());
    }

    @Test
    void deleteUnidadMedidaByAbreviatura_ShouldThrowNotFoundException() {
        when(unidadMedidaRepository.findByAbreviatura("INVALID")).thenReturn(Optional.empty());

        assertThrows(UnidadMedidaNotFoundException.class,
                () -> unidadMedidaService.deleteUnidadMedidaByAbreviatura("INVALID"));
    }
}
