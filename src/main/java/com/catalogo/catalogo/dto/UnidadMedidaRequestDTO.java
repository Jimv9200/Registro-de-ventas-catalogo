package com.catalogo.catalogo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadMedidaRequestDTO {
    
    @NotBlank(message = "El nombre de la unidad de medida no puede estar vacio")
    @Size(min=3, message = "Debe tener al menos 3 caracteres")
    private String name;

    @NotBlank(message = "La abreviatura no puede estar vacia")
    @Size(min = 1)
    private String abreviatura;

    
   
}
