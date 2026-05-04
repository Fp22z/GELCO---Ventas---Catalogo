package com.gelco.backend.repository;

import com.gelco.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Spring Boot es tan inteligente que con solo escribir esto,
    // él hace el "SELECT * FROM usuarios WHERE email = ?" por debajo.
    Optional<Usuario> findByEmail(String email);
}