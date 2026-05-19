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
    public CategoriaResponseDTO createCategoria(CategoriaRequestDTO request){
        return mapCategoria(categoriaRepository.save(buildCategoria(request)));
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO getCategoria(Long id){
        return mapCategoria(categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id)));
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listCategorias(){
        return categoriaRepository.findAll().stream()
        .filter(Categoria::isActive)
        .map(this::mapCategoria)
        .toList();
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> getCategoriasHijas(Long id){
        return categoriaRepository.findByCategoriaPadreId(id).stream()
        .filter(Categoria::isActive)
        .map(this::mapCategoria)
        .toList();
        }

    @Transactional
    public CategoriaResponseDTO updateCategoria(Long id,CategoriaRequestDTO request){
        Categoria cat=categoriaRepository.findById(id).orElseThrow(()-> new CategoriaNotFoundException(id));
        cat.setName(request.getName());
        cat.setDescription(request.getDescription());
        if(request.getIdCategoriaPadre() != null)
            cat.setCategoriaPadre(categoriaRepository.findById(request.getIdCategoriaPadre()).orElseThrow(()-> new CategoriaNotFoundException(id)));
        return mapCategoria(cat);
    }

    @Transactional
    public void deleteCategoria(Long id){
        Categoria cat= categoriaRepository.findById(id).orElseThrow(()-> new CategoriaNotFoundException(id));
        cat.setActive(false);
        
    }

    private Categoria buildCategoria(CategoriaRequestDTO request){
        Categoria cat = new Categoria();
        cat.setName(request.getName());
        cat.setDescription(request.getDescription());
        if(request.getIdCategoriaPadre() != null)
            cat.setCategoriaPadre(categoriaRepository.findById(request.getIdCategoriaPadre()).orElseThrow(()-> new CategoriaNotFoundException(request.getIdCategoriaPadre())));
        
        return cat;
    }

    private CategoriaResponseDTO mapCategoria(Categoria categoria){
        CategoriaResponseDTO catResponse = new CategoriaResponseDTO();
        catResponse.setId(categoria.getId());
        catResponse.setName(categoria.getName());
        catResponse.setDescription(categoria.getDescription());
        if (categoria.getCategoriaPadre() != null) {
            //categoriaRepository.findById(categoria.getCategoriaPadre().getId())
              //      .ifPresent(padre -> catResponse.setNombreCategoriaPadre(padre.getName()));
            
            catResponse.setNombreCategoriaPadre(categoria.getCategoriaPadre().getName());
        }
        return catResponse;
    }

}
