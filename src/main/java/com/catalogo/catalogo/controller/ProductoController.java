package com.catalogo.catalogo.controller;

import java.util.List;

import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.catalogo.catalogo.dto.ProductoRequestDTO;
import com.catalogo.catalogo.dto.ProductoResponseDTO;

import com.catalogo.catalogo.service.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {
    //CRUD PRODUCTOS

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> createProducto(@Valid @RequestBody ProductoRequestDTO producto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.createProduct(producto));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ProductoResponseDTO> getProductoById(@PathVariable String code){
        return ResponseEntity.status(HttpStatus.OK).body(productoService.searchProductoByCode(code));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductoResponseDTO>> searchProductosByNameOrCode(@RequestParam String termino, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        List<ProductoResponseDTO> productos = productoService.searchProductosByNameOrCode(termino, page, size);
        return ResponseEntity.ok(productos);    
    }


    @PutMapping("/{code}")
    public ResponseEntity<ProductoResponseDTO> updateProducto(@PathVariable String code, @Valid @RequestBody ProductoRequestDTO producto){
        return ResponseEntity.status(HttpStatus.OK).body(productoService.updateProduct(code, producto));
    }

    @GetMapping
    public ResponseEntity<Page<ProductoResponseDTO>> listProductosPaginados(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<ProductoResponseDTO> productos = productoService.searchAllProductosPaginados(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(productos);
    }


    @GetMapping("/todos")
    public ResponseEntity<List<ProductoResponseDTO>> listAllProductos(){
        return ResponseEntity.ok(productoService.searchAllProductos());
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<ProductoResponseDTO>> listProductosByCategoria(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(productoService.findProductByCategory(id));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteProducto(@PathVariable String code){
        productoService.deleteProduct(code);
        return ResponseEntity.noContent().build();
    }
}
