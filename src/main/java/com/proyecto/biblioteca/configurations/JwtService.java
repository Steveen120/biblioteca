package com.proyecto.biblioteca.configurations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "asda12345"; // Cambia esto por una clave más segura

    public String generarToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 1 hora de expiración
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }
    public boolean validarToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256("asda12345"))
               .build()
               .verify(token);
            return true;
        } catch (Exception e) {
            return false; // Token inválido o expirado
        }
    }
    
}
