package com.catalogo.catalogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagenProductoResponseDTO {
    private Long id;
    private String url;
    private String altText;
    private boolean principal;
    private int orden;

}
