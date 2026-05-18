package com.catalogo.catalogo.exception;

public class CategoriaNotFoundException extends RuntimeException{
    public CategoriaNotFoundException(Long id){
        super("La categoria con el id "+id+" no existe");
    }

}
