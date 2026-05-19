package com.catalogo.catalogo.repository;


import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.catalogo.catalogo.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long>{
    List<Producto> findByCategoryId(Long categoryId);

    Optional<Producto> findByCode(String code);
    Page<Producto> findByActiveTrue(Pageable pageable);

}
