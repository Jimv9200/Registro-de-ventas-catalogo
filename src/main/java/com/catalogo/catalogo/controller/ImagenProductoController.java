package com.catalogo.catalogo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.catalogo.catalogo.dto.ImagenProductoRequestDTO;
import com.catalogo.catalogo.dto.ImagenProductoResponseDTO;
import com.catalogo.catalogo.service.ImagenProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/imagenes-producto")
@RequiredArgsConstructor
public class ImagenProductoController {

    private final ImagenProductoService imagenProductoService;

    @PostMapping
    public ResponseEntity<ImagenProductoResponseDTO> createImagenProducto(@Valid @RequestBody ImagenProductoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(imagenProductoService.createImagenProducto(request));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ImagenProductoResponseDTO>> listImagenesByProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(imagenProductoService.listImagenPorProducto(productoId));
    }

    @GetMapping("/producto/{productoId}/principal")
    public ResponseEntity<ImagenProductoResponseDTO> getImagenPrincipal(@PathVariable Long productoId) {
        return ResponseEntity.ok(imagenProductoService.showImagenPrincipalProducto(productoId));
    }

    @PutMapping
    public ResponseEntity<ImagenProductoResponseDTO> updateImagenProducto(@Valid @RequestBody ImagenProductoRequestDTO request) {
        return ResponseEntity.ok(imagenProductoService.updateImagen(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImagenProducto(@PathVariable Long id) {
        imagenProductoService.deleteImagenProducto(id);
        return ResponseEntity.noContent().build();
    }
}
