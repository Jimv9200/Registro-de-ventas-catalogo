package com.catalogo.catalogo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.catalogo.catalogo.dto.UnidadMedidaRequestDTO;
import com.catalogo.catalogo.dto.UnidadMedidaResponseDTO;
import com.catalogo.catalogo.exception.UnidadMedidaNotFoundException;
import com.catalogo.catalogo.model.UnidadMedida;
import com.catalogo.catalogo.repository.UnidadMedidaRespository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnidadMedidaService {
    private final UnidadMedidaRespository unidadMedidaRepository;

    //CRUD UNIDADMEDIDA
    @Transactional
    public UnidadMedidaResponseDTO createUnidadMedida(UnidadMedidaRequestDTO requestDTO){
        return mapMedidaResponseDTO(unidadMedidaRepository.save(buildUnidadMedida(requestDTO)));
    }

    @Transactional(readOnly = true)
    public UnidadMedidaResponseDTO searchUnidadMedidaByAbreviatura(String abreviatura){
        UnidadMedida unidadMedida = unidadMedidaRepository.findByAbreviatura(abreviatura).orElseThrow(()-> new UnidadMedidaNotFoundException("No se ha encontrado esta unidad de medida"));
        return mapMedidaResponseDTO(unidadMedida);
    }

    @Transactional(readOnly = true)
    public UnidadMedidaResponseDTO searchUnidadMedidaById(Long id){
        UnidadMedida unidadMedida = unidadMedidaRepository.findById(id).orElseThrow(()-> new UnidadMedidaNotFoundException("No se ha encontrado esta unidad de medida"));
        return mapMedidaResponseDTO(unidadMedida);
    }

    @Transactional(readOnly = true)
    public List<UnidadMedidaResponseDTO> searchAllUnidadesMedida(){
        return unidadMedidaRepository.findAll().stream()
        .filter(UnidadMedida::isActivo)
        .map(this::mapMedidaResponseDTO)
        .toList();
    }

    @Transactional
    public void deleteUnidadMedida(Long id){
        UnidadMedida eliminada= unidadMedidaRepository.findById(id).orElseThrow(()-> new UnidadMedidaNotFoundException("No se ha encontrado esta unidad de medida"));
        eliminada.setActivo(false);
    }

    @Transactional
    public UnidadMedidaResponseDTO updateUnidadMedida(Long id, UnidadMedidaRequestDTO requestDTO){
        UnidadMedida unidadMedida = unidadMedidaRepository.findById(id).orElseThrow(()-> new UnidadMedidaNotFoundException("No se ha encontrado esta unidad de medida"));
        unidadMedida.setName(requestDTO.getName());
        unidadMedida.setAbreviatura(requestDTO.getAbreviatura());
        return mapMedidaResponseDTO(unidadMedida);
    }

    @Transactional
    public void deleteUnidadMedidaByAbreviatura(String abreviatura){
        UnidadMedida eliminada= unidadMedidaRepository.findByAbreviatura(abreviatura).orElseThrow(()-> new UnidadMedidaNotFoundException("No se ha encontrado esta unidad de medida"));
        eliminada.setActivo(false);
    }

    

  
    private UnidadMedidaResponseDTO mapMedidaResponseDTO(UnidadMedida unidadMedida){
        return new UnidadMedidaResponseDTO(
            unidadMedida.getId(),
            unidadMedida.getName(),
            unidadMedida.getAbreviatura()
        );
    }

    private UnidadMedida buildUnidadMedida(UnidadMedidaRequestDTO requestDTO){
        UnidadMedida unidadMedida= new UnidadMedida();
        unidadMedida.setName(requestDTO.getName());
        unidadMedida.setAbreviatura(requestDTO.getAbreviatura());
        unidadMedida.setActivo(true);
        return unidadMedida;
    }
}
