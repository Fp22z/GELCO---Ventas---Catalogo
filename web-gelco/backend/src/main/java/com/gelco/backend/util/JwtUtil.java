package com.gelco.backend.util;

import com.gelco.backend.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${jwt.secret:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
    private String secretKey;

    private final long jwtExpiration = 3600000; // 1 hora

    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("nombre", usuario.getNombre());
        claims.put("perfil", usuario.getPerfil() != null ? usuario.getPerfil().getNombre() : null);

        return Jwts.builder()
                .claims(claims) // Nueva sintaxis
                .subject(usuario.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey()) // El algoritmo se detecta automáticamente
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, Usuario usuario) {
        final String username = extractUsername(token);
        return (username.equals(usuario.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser() // Cambio clave: de parserBuilder() a parser()
                .verifyWith(getSigningKey()) // Cambio clave: de setSigningKey() a verifyWith()
                .build()
                .parseSignedClaims(token) // Cambio clave: de parseClaimsJws() a parseSignedClaims()
                .getPayload(); // Cambio clave: de getBody() a getPayload()
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}