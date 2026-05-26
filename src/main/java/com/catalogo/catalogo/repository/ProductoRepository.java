package com.catalogo.catalogo.repository;


import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.catalogo.catalogo.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long>{
    List<Producto> findByCategoryId(Long categoryId);

    Optional<Producto> findByCode(String code);
    Page<Producto> findByActiveTrue(Pageable pageable);

    @Query(value = "SELECT * FROM producto WHERE nombre COLLATE utf8mb4_unicode_ci LIKE %:termino%", nativeQuery = true)
    List<Producto> buscarIgnorandoTildes(@Param("termino") String termino);

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE CONCAT('%', :termino, '%') OR p.codigo LIKE CONCAT('%', :termino, '%')")
    List<Producto> buscarPorNombreOCodigo(@Param("termino") String termino, Pageable pageable);

}
