package com.gelco.backend.controller;

import com.gelco.backend.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
@Tag(name = "Productos")
public class ProductoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProductoService productoService;

    @GetMapping
    @Operation(summary = "Obtiene la lista de todos los productos registrados")
    public ResponseEntity<?> getProductos() {
        try {
            logger.info("Solicitud recibida para listar productos");
            return ResponseEntity.ok(productoService.getProductos());
        } catch (Exception e) {
            logger.error("Error al obtener los productos: {}", e.getMessage());
            return new ResponseEntity<>("Error interno al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}