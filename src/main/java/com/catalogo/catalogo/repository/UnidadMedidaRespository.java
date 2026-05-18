package com.catalogo.catalogo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalogo.catalogo.model.UnidadMedida;

@Repository
public interface UnidadMedidaRespository extends JpaRepository<UnidadMedida, Long>{

    Optional<UnidadMedida> findByAbreviatura(String abreviatura);
}
