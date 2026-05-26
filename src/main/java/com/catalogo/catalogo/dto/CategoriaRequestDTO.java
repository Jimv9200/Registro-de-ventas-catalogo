package com.catalogo.catalogo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequestDTO {

    @NotBlank(message = "El nombre de la categoria no puede estar vacío") @Size(min = 3, max = 100, message = "El campo 'nombre de categoría' debe tener entre 3 y 25 caracteres")
    private String name;

    @NotBlank(message = "La descripcion de la categoría no puede estar vacío") @Size(min = 3, max = 100, message = "El campo 'descripción' debe tener entre 3 y 25 caracteres")
    private String description;

    
    private Long categoriaPadreId;

}
