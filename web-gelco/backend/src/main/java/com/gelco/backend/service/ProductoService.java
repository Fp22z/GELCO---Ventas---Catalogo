package com.gelco.backend.service;

import com.gelco.backend.model.Producto;
import com.gelco.backend.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }
}