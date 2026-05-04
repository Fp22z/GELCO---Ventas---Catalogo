package com.gelco.backend.controller;

import com.gelco.backend.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation; //
import io.swagger.v3.oas.annotations.tags.Tag; //
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/v1/productos")
@CrossOrigin(origins = "*")
@Tag(name = "Productos", description = "API para la gestión del catálogo de productos de GELCO") // Título en Swagger
public class ProductoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductoService productoService;

    @GetMapping
    @Operation(summary = "Obtiene la lista de todos los productos registrados") // Descripción de la ruta en Swagger
    public ResponseEntity<?> getProductos() {
        try {
            return ResponseEntity.ok(productoService.getProductos());
        } catch (Exception e) {
            logger.error("Error al obtener los productos: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}