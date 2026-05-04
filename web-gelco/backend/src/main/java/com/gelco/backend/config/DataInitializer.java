package com.gelco.backend.config;

import com.gelco.backend.model.Usuario;
import com.gelco.backend.repository.PerfilRepository;
import com.gelco.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByEmail("admin@gelco.com").isEmpty()) {
            var perfilAdmin = perfilRepository.findByNombre("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: El perfil ADMIN no existe en la BD"));

            var admin = Usuario.builder()
                    .nombre("Admin Principal")
                    .email("admin@gelco.com")
                    .passwordHash(passwordEncoder.encode("123456")) // Aquí Java crea el Hash perfecto
                    .perfil(perfilAdmin)
                    .estado(true)
                    .build();

            usuarioRepository.save(admin);
            System.out.println(">>> Usuario ADMIN creado exitosamente con BCrypt");
        }
    }
}