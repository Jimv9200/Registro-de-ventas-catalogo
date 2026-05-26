package com.catalogo.catalogo.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {


    private String name;
    private String description;
    private String sku;
    private String code;
    private BigDecimal price;
    private String categoryName;
    private BigDecimal iva;
    private String unidadMedida;
    private BigDecimal purchasePrice;

}
