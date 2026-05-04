package com.gelco.backend.service;

import com.gelco.backend.model.Producto;
import com.gelco.backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    ProductoRepository productoRepository;

    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }
}