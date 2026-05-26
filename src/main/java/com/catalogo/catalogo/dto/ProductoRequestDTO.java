package com.catalogo.catalogo.dto;

import java.math.BigDecimal;




import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {

    @NotBlank(message = "Asigne un nombre valido al producto") @Size(min = 3, message = "El nombre del producto debe tener al menos 3 caracteres")
    private String name;

    @NotNull @Positive(message = "El precio de compra debe de ser mayor de 0 y positivo")
    private BigDecimal purchasePrice;

    private String description;

    @NotNull @Positive(message = "El precio debe de ser mayor de 0 y positivo")
    private BigDecimal price;

    @NotNull
    private Long categoryId;

    private Long unidadMedidaId;

    @NotBlank
    private String code;

    private String sku;

    @PositiveOrZero
    private BigDecimal iva;

}
