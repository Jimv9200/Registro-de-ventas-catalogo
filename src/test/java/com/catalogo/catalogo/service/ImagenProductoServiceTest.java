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

import com.catalogo.catalogo.dto.ImagenProductoRequestDTO;
import com.catalogo.catalogo.dto.ImagenProductoResponseDTO;
import com.catalogo.catalogo.exception.ImagenProductoNotFoundException;
import com.catalogo.catalogo.exception.ProductoNotFoundException;
import com.catalogo.catalogo.model.ImagenProducto;
import com.catalogo.catalogo.model.Producto;
import com.catalogo.catalogo.repository.ImagenProductoRepository;
import com.catalogo.catalogo.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
class ImagenProductoServiceTest {

    @Mock
    private ImagenProductoRepository imagenProductoRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ImagenProductoService imagenProductoService;

    private Producto producto = new Producto();

    private ImagenProducto buildImagen() {
        ImagenProducto img = new ImagenProducto();
        img.setId(1L);
        img.setUrl("http://example.com/img.jpg");
        img.setPrincipal(true);
        img.setOrden(1);
        img.setProducto(producto);
        img.setActive(true);
        return img;
    }

    private ImagenProductoRequestDTO buildRequest() {
        ImagenProductoRequestDTO req = new ImagenProductoRequestDTO();
        req.setId(1L);
        req.setUrl("http://example.com/img.jpg");
        req.setPrincipal(true);
        req.setOrden(1);
        req.setProductoId(1L);
        return req;
    }

    @Test
    void createImagenProducto_ShouldReturnResponseDTO() {
        ImagenProductoRequestDTO request = buildRequest();
        request.setId(null);
        ImagenProducto saved = buildImagen();

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(imagenProductoRepository.save(any(ImagenProducto.class))).thenReturn(saved);

        ImagenProductoResponseDTO result = imagenProductoService.createImagenProducto(request);

        assertNotNull(result);
        assertEquals("http://example.com/img.jpg", result.getUrl());
        assertTrue(result.isPrincipal());
    }

    @Test
    void createImagenProducto_ShouldThrowProductoNotFoundException() {
        ImagenProductoRequestDTO request = buildRequest();
        request.setId(null);

        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        request.setProductoId(99L);
        assertThrows(ProductoNotFoundException.class, () -> imagenProductoService.createImagenProducto(request));
    }

    @Test
    void showImagenPrincipalProducto_ShouldReturnResponseDTO() {
        ImagenProducto img = buildImagen();

        when(imagenProductoRepository.findByProductoIdAndPrincipal(1L, true)).thenReturn(Optional.of(img));

        ImagenProductoResponseDTO result = imagenProductoService.showImagenPrincipalProducto(1L);

        assertNotNull(result);
        assertTrue(result.isPrincipal());
    }

    @Test
    void showImagenPrincipalProducto_ShouldThrowNotFoundException() {
        when(imagenProductoRepository.findByProductoIdAndPrincipal(1L, true)).thenReturn(Optional.empty());

        assertThrows(ImagenProductoNotFoundException.class, () -> imagenProductoService.showImagenPrincipalProducto(1L));
    }

    @Test
    void listImagenPorProducto_ShouldReturnList() {
        ImagenProducto img1 = buildImagen();
        ImagenProducto img2 = buildImagen();
        img2.setPrincipal(false);

        when(imagenProductoRepository.findByProductoId(1L)).thenReturn(List.of(img1, img2));

        List<ImagenProductoResponseDTO> result = imagenProductoService.listImagenPorProducto(1L);

        assertEquals(2, result.size());
    }

    @Test
    void updateImagen_ShouldReturnUpdatedDTO() {
        ImagenProducto existing = buildImagen();
        ImagenProductoRequestDTO request = buildRequest();
        request.setUrl("http://example.com/updated.jpg");

        when(imagenProductoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        ImagenProductoResponseDTO result = imagenProductoService.updateImagen(request);

        assertNotNull(result);
        assertEquals("http://example.com/updated.jpg", result.getUrl());
    }

    @Test
    void updateImagen_ShouldThrowImagenNotFoundException() {
        ImagenProductoRequestDTO request = buildRequest();

        when(imagenProductoRepository.findById(99L)).thenReturn(Optional.empty());

        request.setId(99L);
        assertThrows(ImagenProductoNotFoundException.class, () -> imagenProductoService.updateImagen(request));
    }

    @Test
    void deleteImagenProducto_ShouldSetActiveFalse() {
        ImagenProducto img = buildImagen();

        when(imagenProductoRepository.findById(1L)).thenReturn(Optional.of(img));

        imagenProductoService.deleteImagenProducto(1L);

        assertFalse(img.isActive());
    }

    @Test
    void deleteImagenProducto_ShouldThrowNotFoundException() {
        when(imagenProductoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ImagenProductoNotFoundException.class, () -> imagenProductoService.deleteImagenProducto(99L));
    }
}
