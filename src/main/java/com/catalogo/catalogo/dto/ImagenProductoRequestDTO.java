package com.catalogo.catalogo.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagenProductoRequestDTO {

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long id;
    @NotBlank(message = "La URL de la imagen no puede estar vacía")
    private String url;

    private boolean principal;
    private int orden;

    private Long productoId;
}
