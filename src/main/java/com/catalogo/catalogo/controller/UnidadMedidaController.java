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

import com.catalogo.catalogo.dto.UnidadMedidaRequestDTO;
import com.catalogo.catalogo.dto.UnidadMedidaResponseDTO;
import com.catalogo.catalogo.service.UnidadMedidaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/unidades-medida")
@RequiredArgsConstructor
public class UnidadMedidaController {

    private final UnidadMedidaService unidadMedidaService;

    @PostMapping
    public ResponseEntity<UnidadMedidaResponseDTO> createUnidadMedida(@Valid @RequestBody UnidadMedidaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadMedidaService.createUnidadMedida(request));
    }

    @GetMapping
    public ResponseEntity<List<UnidadMedidaResponseDTO>> listAllUnidadesMedida() {
        return ResponseEntity.ok(unidadMedidaService.searchAllUnidadesMedida());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadMedidaResponseDTO> getUnidadMedidaById(@PathVariable Long id) {
        return ResponseEntity.ok(unidadMedidaService.searchUnidadMedidaById(id));
    }

    @GetMapping("/abreviatura/{abreviatura}")
    public ResponseEntity<UnidadMedidaResponseDTO> getUnidadMedidaByAbreviatura(@PathVariable String abreviatura) {
        return ResponseEntity.ok(unidadMedidaService.searchUnidadMedidaByAbreviatura(abreviatura));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadMedidaResponseDTO> updateUnidadMedida(@PathVariable Long id, @Valid @RequestBody UnidadMedidaRequestDTO request) {
        return ResponseEntity.ok(unidadMedidaService.updateUnidadMedida(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnidadMedida(@PathVariable Long id) {
        unidadMedidaService.deleteUnidadMedida(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/abreviatura/{abreviatura}")
    public ResponseEntity<Void> deleteUnidadMedidaByAbreviatura(@PathVariable String abreviatura) {
        unidadMedidaService.deleteUnidadMedidaByAbreviatura(abreviatura);
        return ResponseEntity.noContent().build();
    }
}
