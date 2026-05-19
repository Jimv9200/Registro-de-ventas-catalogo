package com.catalogo.catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalogo.catalogo.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{


    List<Categoria> findByCategoriaPadreId(Long id);

}
