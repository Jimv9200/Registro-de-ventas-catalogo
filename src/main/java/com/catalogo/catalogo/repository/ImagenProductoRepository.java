package com.catalogo.catalogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalogo.catalogo.model.ImagenProducto;

@Repository
public interface ImagenProductoRepository extends JpaRepository<ImagenProducto, Long>{
    List<ImagenProducto> findByProductoId(Long id);
    Optional<ImagenProducto> findByProductoIdAndPrincipal(Long productoId, boolean principal);

}
