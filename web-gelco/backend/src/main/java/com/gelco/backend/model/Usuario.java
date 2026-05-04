package com.gelco.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="usuarios")
public class Usuario implements UserDetails { // <--- AHORA IMPLEMENTA UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    @Column(nullable = false)
    private Boolean estado;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // =========================================================
    // MÉTODOS OBLIGATORIOS DE SPRING SECURITY (UserDetails)
    // =========================================================

    // 1. ¿Qué rol/perfil tiene este usuario?
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + perfil.getNombre()));
    }

    // 2. ¿Cuál es su contraseña?
    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    // 3. ¿Cuál es el campo que usa para iniciar sesión? (En nuestro caso, el email)
    @Override
    public String getUsername() {
        return this.email;
    }

    // 4. ¿La cuenta está activa? (Usamos tu campo "estado")
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.estado;
    }
}