package com.gelco.backend.service;

import com.gelco.backend.dto.AuthResponseDTO;
import com.gelco.backend.dto.LoginRequestDTO;
import com.gelco.backend.dto.RegisterRequestDTO;
import com.gelco.backend.model.Usuario;
import com.gelco.backend.repository.UsuarioRepository;
import com.gelco.backend.repository.PerfilRepository;
import com.gelco.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent())
            throw new IllegalArgumentException("Hay un usuario registrado con ese email");

        var perfilDefault = perfilRepository.findByNombre("CONSULTORA")
                .orElseThrow(() -> new RuntimeException("Perfil por defecto no encontrado"));

        var usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .perfil(perfilDefault)
                .estado(true)
                .createdAt(LocalDateTime.now())
                .build();

        usuarioRepository.save(usuario);

        var token = jwtService.generateToken(usuario);
        return AuthResponseDTO.builder()
                .token(token)
                .build();
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        var usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email no encontrado"));

    if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
        throw new BadCredentialsException("Contraseña incorrecta");
    }
    
        var token = jwtService.generateToken(usuario);

        return AuthResponseDTO.builder()
                .token(token)
                .build();
    }
}