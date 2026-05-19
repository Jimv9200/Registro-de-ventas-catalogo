package com.catalogo.catalogo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.catalogo.catalogo.dto.ProductoRequestDTO;
import com.catalogo.catalogo.dto.ProductoResponseDTO;
import com.catalogo.catalogo.exception.CategoriaNotFoundException;
import com.catalogo.catalogo.exception.ProductoNotFoundException;
import com.catalogo.catalogo.model.Categoria;
import com.catalogo.catalogo.model.Producto;
import com.catalogo.catalogo.repository.CategoriaRepository;
import com.catalogo.catalogo.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProductoService productoService;

    private Categoria categoria = new Categoria(1L, "Cat1", "Desc", null, true);

    private ProductoRequestDTO buildRequest() {
        ProductoRequestDTO req = new ProductoRequestDTO();
        req.setName("Producto1");
        req.setDescription("Desc");
        req.setPrice(BigDecimal.valueOf(100));
        req.setCategoryId(1L);
        req.setCode("PROD001");
        req.setSku("SKU001");
        req.setIva(BigDecimal.valueOf(16));
        return req;
    }

    private Producto buildProducto() {
        Producto p = new Producto();
        p.setName("Producto1");
        p.setDescription("Desc");
        p.setPrice(BigDecimal.valueOf(100));
        p.setCategory(categoria);
        p.setCode("PROD001");
        p.setSku("SKU001");
        p.setIva(BigDecimal.valueOf(16));
        p.setActive(true);
        return p;
    }

    @Test
    void createProduct_ShouldReturnResponseDTO() {
        ProductoRequestDTO request = buildRequest();
        Producto saved = buildProducto();

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(productoRepository.save(any(Producto.class))).thenReturn(saved);

        ProductoResponseDTO result = productoService.createProduct(request);

        assertNotNull(result);
        assertEquals("Producto1", result.getName());
        assertEquals("PROD001", result.getCode());
    }

    @Test
    void createProduct_ShouldThrowCategoriaNotFoundException() {
        ProductoRequestDTO request = buildRequest();

        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> productoService.createProduct(request));
    }

    @Test
    void searchProductoByCode_ShouldReturnResponseDTO() {
        Producto producto = buildProducto();

        when(productoRepository.findByCode("PROD001")).thenReturn(Optional.of(producto));

        ProductoResponseDTO result = productoService.searchProductoByCode("PROD001");

        assertNotNull(result);
        assertEquals("PROD001", result.getCode());
    }

    @Test
    void searchProductoByCode_ShouldThrowNotFoundException() {
        when(productoRepository.findByCode("INVALID")).thenReturn(Optional.empty());

        assertThrows(ProductoNotFoundException.class, () -> productoService.searchProductoByCode("INVALID"));
    }

    @Test
    void searchAllProductosPaginados_ShouldReturnPage() {
        Page<Producto> page = new PageImpl<>(List.of(buildProducto()));

        when(productoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<ProductoResponseDTO> result = productoService.searchAllProductosPaginados(0, 10);

        assertEquals(1, result.getContent().size());
        assertEquals("Producto1", result.getContent().get(0).getName());
    }

    @Test
    void findProductByCategory_ShouldReturnList() {
        when(productoRepository.findByCategoryId(1L)).thenReturn(List.of(buildProducto()));

        List<ProductoResponseDTO> result = productoService.findProductByCategory(1L);

        assertEquals(1, result.size());
        assertEquals("Producto1", result.get(0).getName());
    }

    @Test
    void updateProduct_ShouldReturnUpdatedDTO() {
        Producto existing = buildProducto();
        ProductoRequestDTO request = buildRequest();
        request.setName("Updated");
        request.setPrice(BigDecimal.valueOf(200));

        when(productoRepository.findByCode("PROD001")).thenReturn(Optional.of(existing));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        ProductoResponseDTO result = productoService.updateProduct("PROD001", request);

        assertNotNull(result);
        assertEquals("Updated", result.getName());
        assertEquals(BigDecimal.valueOf(200), result.getPrice());
    }

    @Test
    void updateProduct_ShouldThrowProductoNotFoundException() {
        ProductoRequestDTO request = buildRequest();

        when(productoRepository.findByCode("INVALID")).thenReturn(Optional.empty());

        assertThrows(ProductoNotFoundException.class, () -> productoService.updateProduct("INVALID", request));
    }

    @Test
    void updateProduct_ShouldThrowCategoriaNotFoundException() {
        Producto existing = buildProducto();
        ProductoRequestDTO request = buildRequest();

        when(productoRepository.findByCode("PROD001")).thenReturn(Optional.of(existing));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> productoService.updateProduct("PROD001", request));
    }

    @Test
    void deleteProduct_ShouldSetActiveFalse() {
        Producto producto = buildProducto();

        when(productoRepository.findByCode("PROD001")).thenReturn(Optional.of(producto));

        productoService.deleteProduct("PROD001");

        assertFalse(producto.isActive());
    }

    @Test
    void deleteProduct_ShouldThrowNotFoundException() {
        when(productoRepository.findByCode("INVALID")).thenReturn(Optional.empty());

        assertThrows(ProductoNotFoundException.class, () -> productoService.deleteProduct("INVALID"));
    }
}
