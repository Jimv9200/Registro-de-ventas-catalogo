package com.catalogo.catalogo.exception;

public class ProductoNotFoundException extends RuntimeException{
    public ProductoNotFoundException(String id){
        super("El producto con el codigo: "+ id+ " no se encuentra registrado en la base de datos");
    }

}
