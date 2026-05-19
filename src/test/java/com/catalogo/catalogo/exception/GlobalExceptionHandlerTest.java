package com.catalogo.catalogo.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @RestController
    @RequestMapping("/test")
    static class TestController {

        @GetMapping("/categoria-not-found")
        public void throwCategoriaNotFound() {
            throw new CategoriaNotFoundException(99L);
        }

        @GetMapping("/producto-not-found")
        public void throwProductoNotFound() {
            throw new ProductoNotFoundException("PROD001");
        }

        @GetMapping("/imagen-not-found")
        public void throwImagenNotFound() {
            throw new ImagenProductoNotFoundException("Imagen no encontrada");
        }

        @GetMapping("/unidad-not-found")
        public void throwUnidadNotFound() {
            throw new UnidadMedidaNotFoundException("Unidad no encontrada");
        }

        @GetMapping("/illegal-argument")
        public void throwIllegalArgument() {
            throw new IllegalArgumentException("Argumento invalido");
        }

        @GetMapping("/generic-error")
        public void throwGenericError() {
            throw new RuntimeException("Error inesperado");
        }

        @PostMapping("/invalid-body")
        public void postInvalidBody() {
        }
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void handleCategoriaNotFound_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/test/categoria-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigoError").value("CATEGORIA_NOT_FOUND"))
                .andExpect(jsonPath("$.mensaje").value("La categoria con el id 99 no existe"));
    }

    @Test
    void handleProductoNotFound_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/test/producto-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigoError").value("PRODUCTO_NOT_FOUND"))
                .andExpect(jsonPath("$.mensaje").value("PROD001"));
    }

    @Test
    void handleImagenNotFound_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/test/imagen-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigoError").value("IMAGEN_NOT_FOUND"))
                .andExpect(jsonPath("$.mensaje").value("Imagen no encontrada"));
    }

    @Test
    void handleUnidadMedidaNotFound_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/test/unidad-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigoError").value("UNIDAD_MEDIDA_NOT_FOUND"))
                .andExpect(jsonPath("$.mensaje").value("Unidad no encontrada"));
    }

    @Test
    void handleIllegalArgument_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/test/illegal-argument"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigoError").value("ILLEGAL_ARGUMENT"))
                .andExpect(jsonPath("$.mensaje").value("Argumento invalido"));
    }

    @Test
    void handleGenericException_ShouldReturn500() throws Exception {
        mockMvc.perform(get("/test/generic-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.codigoError").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.mensaje").value("Error interno del servidor"));
    }
}
