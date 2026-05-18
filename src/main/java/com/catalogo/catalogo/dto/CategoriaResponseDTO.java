package com.catalogo.catalogo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaResponseDTO {

    private Long id;
    
    
    private String name;

    
    private String description;

      
    private String nombreCategoriaPadre;

    
    
}
