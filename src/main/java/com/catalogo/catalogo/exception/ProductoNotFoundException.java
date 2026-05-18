package com.catalogo.catalogo.exception;

public class ProductoNotFoundException extends RuntimeException{
    public ProductoNotFoundException(String message){
        super(message);
    }

}
