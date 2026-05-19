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

import com.catalogo.catalogo.dto.CategoriaRequestDTO;
import com.catalogo.catalogo.dto.CategoriaResponseDTO;
import com.catalogo.catalogo.service.CategoriaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    //CRUD CATALOGO
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> createCategoria(@Valid @RequestBody CategoriaRequestDTO categoria){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.createCategoria(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> getCategoriaById(@PathVariable Long id){
        return ResponseEntity.ok(categoriaService.getCategoria(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> updateCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO categoria){
            return ResponseEntity.ok(categoriaService.updateCategoria(id, categoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listCategoria(){
        return ResponseEntity.ok(categoriaService.listCategorias());
    }
    @GetMapping("/{id}/hijas")
    public ResponseEntity<List<CategoriaResponseDTO>> listCategoriasHijas(@PathVariable Long id){
        return ResponseEntity.ok(categoriaService.getCategoriasHijas(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id){
        categoriaService.deleteCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
