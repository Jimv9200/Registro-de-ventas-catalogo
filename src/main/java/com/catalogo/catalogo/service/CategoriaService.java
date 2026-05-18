package com.catalogo.catalogo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.catalogo.catalogo.dto.CategoriaRequestDTO;
import com.catalogo.catalogo.dto.CategoriaResponseDTO;
import com.catalogo.catalogo.exception.CategoriaNotFoundException;
import com.catalogo.catalogo.model.Categoria;
import com.catalogo.catalogo.repository.CategoriaRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;



    @Transactional
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO request){
        return mapearCategoria(categoriaRepository.save(construirCategoria(request)));
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO obtenerCategoria(Long id){
        return mapearCategoria(categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id)));
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> obtenerCategorias(){
        return categoriaRepository.findAll().stream()
        .map(this::mapearCategoria)
        .toList();
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> obtenerCategoriasHijas(Long id){
        return categoriaRepository.obtenerCategoriasHijasById(id).stream()
        .map(this::mapearCategoria)
        .toList();
        }

    @Transactional
    public CategoriaResponseDTO actualizarCategoria(Long id,CategoriaRequestDTO request){
        Categoria cat=categoriaRepository.findById(id).orElseThrow(()-> new CategoriaNotFoundException(id));
        cat.setName(request.getName());
        cat.setDescription(request.getDescription());
        cat.setIdCategoriaPadre(categoriaRepository.findById(request.getIdCategoriaPadre()).orElseThrow(()-> new CategoriaNotFoundException(id)));
        return mapearCategoria(cat);
    }

    @Transactional
    public void eliminarCategoria(Long id){
        Categoria cat= categoriaRepository.findById(id).orElseThrow(()-> new CategoriaNotFoundException(id));
        cat.setActivo(false);
        
    }

    private Categoria construirCategoria(CategoriaRequestDTO request){
        Categoria cat = new Categoria();
        cat.setName(request.getName());
        cat.setDescription(request.getDescription());
        cat.setIdCategoriaPadre(categoriaRepository.findById(request.getIdCategoriaPadre()).orElseThrow(()-> new CategoriaNotFoundException(request.getIdCategoriaPadre())));
        
        return cat;
    }

    private CategoriaResponseDTO mapearCategoria(Categoria categoria){
        CategoriaResponseDTO catResponse = new CategoriaResponseDTO();
        catResponse.setId(categoria.getId());
        catResponse.setName(categoria.getName());
        catResponse.setDescription(categoria.getDescription());
        if (categoria.getIdCategoriaPadre() != null) {
            categoriaRepository.findById(categoria.getIdCategoriaPadre().getId())
                    .ifPresent(padre -> catResponse.setNombreCategoriaPadre(padre.getName()));
        }
        return catResponse;
    }

}
