package com.gelco.backend.repository;

import com.gelco.backend.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    // Nos servirá para buscar el perfil por su nombre (Ej: "ADMIN")
    Optional<Perfil> findByNombre(String nombre);
}