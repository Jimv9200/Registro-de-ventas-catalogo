package com.catalogo.catalogo.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.catalogo.catalogo.dto.ErrorResponseDTO;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCategoriaNotFound(CategoriaNotFoundException ex) {
        log.error("Categoria no encontrada: {}", ex.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "CATEGORIA_NOT_FOUND");
    }

    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductoNotFound(ProductoNotFoundException ex) {
        log.error("Producto no encontrado: {}", ex.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "PRODUCTO_NOT_FOUND");
    }

    @ExceptionHandler(ImagenProductoNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleImagenProductoNotFound(ImagenProductoNotFoundException ex) {
        log.error("Imagen de producto no encontrada: {}", ex.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "IMAGEN_NOT_FOUND");
    }

    @ExceptionHandler(UnidadMedidaNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnidadMedidaNotFound(UnidadMedidaNotFoundException ex) {
        log.error("Unidad de medida no encontrada: {}", ex.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "UNIDAD_MEDIDA_NOT_FOUND");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error("Error de validacion: {}", errors);
        return buildResponse(HttpStatus.BAD_REQUEST, errors, "VALIDATION_ERROR");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(ConstraintViolationException ex) {
        log.error("Violacion de restriccion: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "CONSTRAINT_VIOLATION");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("Violacion de integridad de datos: {}", ex.getMessage());
        return buildResponse(HttpStatus.CONFLICT, "Conflicto de integridad de datos", "DATA_INTEGRITY_VIOLATION");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("Cuerpo de solicitud no legible: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "Cuerpo de la solicitud invalido o mal formado", "MALFORMED_REQUEST");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingParam(MissingServletRequestParameterException ex) {
        log.error("Parametro faltante: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "Parametro requerido faltante: " + ex.getParameterName(), "MISSING_PARAMETER");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.error("Tipo de argumento invalido: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "Tipo de argumento invalido para: " + ex.getName(), "TYPE_MISMATCH");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        log.error("Tipo de medio no soportado: {}", ex.getMessage());
        return buildResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Tipo de contenido no soportado", "UNSUPPORTED_MEDIA_TYPE");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoHandlerFound(NoHandlerFoundException ex) {
        log.error("Endpoint no encontrado: {}", ex.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, "El recurso solicitado no existe", "ENDPOINT_NOT_FOUND");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException ex) {
        log.error("Argumento invalido: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "ILLEGAL_ARGUMENT");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex) {
        log.error("Error interno del servidor: ", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", "INTERNAL_ERROR");
    }

    private ResponseEntity<ErrorResponseDTO> buildResponse(HttpStatus status, String mensaje, String codigoError) {
        ErrorResponseDTO error = new ErrorResponseDTO(mensaje, codigoError, LocalDateTime.now().toString());
        return new ResponseEntity<>(error, status);
    }
}
