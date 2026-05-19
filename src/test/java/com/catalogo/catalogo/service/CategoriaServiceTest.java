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

import com.catalogo.catalogo.dto.CategoriaRequestDTO;
import com.catalogo.catalogo.dto.CategoriaResponseDTO;
import com.catalogo.catalogo.exception.CategoriaNotFoundException;
import com.catalogo.catalogo.model.Categoria;
import com.catalogo.catalogo.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void createCategoria_ShouldReturnResponseDTO() {
        Categoria padre = new Categoria(1L, "Padre", "Desc", null, true);
        CategoriaRequestDTO request = new CategoriaRequestDTO("Test", "Descripcion", 1L);
        Categoria saved = new Categoria(2L, "Test", "Descripcion", padre, true);

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(padre));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(saved);

        CategoriaResponseDTO result = categoriaService.createCategoria(request);

        assertNotNull(result);
        assertEquals("Test", result.getName());
        assertEquals("Descripcion", result.getDescription());
    }

    @Test
    void getCategoria_ShouldReturnResponseDTO() {
        Categoria cat = new Categoria(1L, "Test", "Descripcion", null, true);

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(cat));

        CategoriaResponseDTO result = categoriaService.getCategoria(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test", result.getName());
    }

    @Test
    void getCategoria_ShouldThrowNotFoundException() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> categoriaService.getCategoria(99L));
    }

    @Test
    void listCategorias_ShouldReturnList() {
        List<Categoria> categorias = List.of(
                new Categoria(1L, "Cat1", "Desc1", null, true),
                new Categoria(2L, "Cat2", "Desc2", null, true));

        when(categoriaRepository.findAll()).thenReturn(categorias);

        List<CategoriaResponseDTO> result = categoriaService.listCategorias();

        assertEquals(2, result.size());
        assertEquals("Cat1", result.get(0).getName());
    }

    @Test
    void getCategoriasHijas_ShouldReturnList() {
        Categoria padre = new Categoria(1L, "Padre", "Desc", null, true);
        List<Categoria> hijas = List.of(
                new Categoria(2L, "Hija1", "Desc", padre, true));

        when(categoriaRepository.findByCategoriaPadreId(1L)).thenReturn(hijas);

        List<CategoriaResponseDTO> result = categoriaService.getCategoriasHijas(1L);

        assertEquals(1, result.size());
        assertEquals("Hija1", result.get(0).getName());
    }

    @Test
    void updateCategoria_ShouldReturnUpdatedDTO() {
        Categoria padre = new Categoria(2L, "Padre", "Desc", null, true);
        Categoria existing = new Categoria(1L, "Old", "Old Desc", padre, true);
        CategoriaRequestDTO request = new CategoriaRequestDTO("New", "New Desc", 2L);

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoriaRepository.findById(2L)).thenReturn(Optional.of(padre));

        CategoriaResponseDTO result = categoriaService.updateCategoria(1L, request);

        assertNotNull(result);
        assertEquals("New", result.getName());
        assertEquals("New Desc", result.getDescription());
    }

    @Test
    void updateCategoria_ShouldThrowNotFoundException() {
        CategoriaRequestDTO request = new CategoriaRequestDTO("Test", "Desc", null);

        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> categoriaService.updateCategoria(99L, request));
    }

    @Test
    void deleteCategoria_ShouldSetInactivo() {
        Categoria cat = new Categoria(1L, "Test", "Desc", null, true);

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(cat));

        categoriaService.deleteCategoria(1L);

        assertFalse(cat.isActive());
    }

    @Test
    void deleteCategoria_ShouldThrowNotFoundException() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> categoriaService.deleteCategoria(99L));
    }
}
