package com.catalogo.catalogo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.catalogo.catalogo.dto.ImagenProductoRequestDTO;
import com.catalogo.catalogo.dto.ImagenProductoResponseDTO;
import com.catalogo.catalogo.exception.ImagenProductoNotFoundException;
import com.catalogo.catalogo.exception.ProductoNotFoundException;
import com.catalogo.catalogo.model.ImagenProducto;
import com.catalogo.catalogo.repository.ImagenProductoRepository;
import com.catalogo.catalogo.repository.ProductoRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImagenProductoService {

    private final ImagenProductoRepository imagenProductoRepository;
    private final ProductoRepository productoRepository;

    //CRUD IMAGEN PRODUCTO

    @Transactional
    public ImagenProductoResponseDTO createImagenProducto(ImagenProductoRequestDTO imagenProducto){
        return mapToResponseDTO(buildImagenProducto(imagenProducto));
    }

    @Transactional(readOnly = true)
    public ImagenProductoResponseDTO showImagenPrincipalProducto(Long id){
        ImagenProducto imagenPrincipal= imagenProductoRepository.findByProductoIdAndPrincipal(id, true).orElseThrow(() -> new ImagenProductoNotFoundException("No se ha encontrado la imagen del producto: "));
        
        return mapToResponseDTO(imagenPrincipal);
    }

    @Transactional(readOnly = true)
    public List<ImagenProductoResponseDTO> listImagenPorProducto(Long idProducto){
        return imagenProductoRepository.findByProductoId(idProducto).stream()
        .filter(ImagenProducto::isActive)
        .map(this::mapToResponseDTO)
        .toList();
    }

    @Transactional
    public ImagenProductoResponseDTO updateImagen(ImagenProductoRequestDTO requestDTO){
        ImagenProducto update= imagenProductoRepository.findById(requestDTO.getId()).orElseThrow(()-> new ImagenProductoNotFoundException("No se ha encontrado la imagen"));
        update.setUrl(requestDTO.getUrl());
        update.setPrincipal(requestDTO.isPrincipal());
        update.setOrden(requestDTO.getOrden());
        update.setProducto(productoRepository.findById(requestDTO.getProductoId()).orElseThrow(()-> new ProductoNotFoundException("Producto no encontrado")));
        update.setActive(true);
        return mapToResponseDTO(update);
    }

    private ImagenProductoResponseDTO mapToResponseDTO(ImagenProducto imagenProducto) {
        ImagenProductoResponseDTO response= new ImagenProductoResponseDTO();
        response.setId(imagenProducto.getId());
        response.setOrden(imagenProducto.getOrden());
        response.setPrincipal(imagenProducto.isPrincipal());
        response.setUrl(imagenProducto.getUrl());
        return response;
    }

    private ImagenProducto buildImagenProducto(ImagenProductoRequestDTO requestDTO){
         ImagenProducto nuevaImagen = new ImagenProducto();
        nuevaImagen.setUrl(requestDTO.getUrl());
        nuevaImagen.setPrincipal(requestDTO.isPrincipal());
        nuevaImagen.setOrden(requestDTO.getOrden());
        nuevaImagen.setProducto(productoRepository.findById(requestDTO.getProductoId()).orElseThrow(()-> new ProductoNotFoundException("Producto no encontrado")));
        nuevaImagen.setActive(true);
        ImagenProducto imagenGuardada = imagenProductoRepository.save(nuevaImagen);
        return imagenGuardada;
    }


    @Transactional
    public void deleteImagenProducto(Long id){
        ImagenProducto eliminado= imagenProductoRepository.findById(id).orElseThrow(()-> new ImagenProductoNotFoundException("No se encontro la imagen con id: "+id));
        eliminado.setActive(false);
    }
}
