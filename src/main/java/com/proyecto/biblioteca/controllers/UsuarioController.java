package com.proyecto.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.proyecto.biblioteca.models.Usuario;
import com.proyecto.biblioteca.services.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Registro de usuarios con el rol especificado en el cuerpo de la solicitud
    @PostMapping("/registro")
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
        // Validación para asegurar que el email no esté en uso
        if (usuarioService.buscarPorEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        return usuarioService.registrarUsuario(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Autenticando usuario: " + loginRequest.getEmail());
            return ResponseEntity.ok("Autenticación exitosa");
        } catch (Exception e) {
            // Captura cualquier excepción que ocurra durante la autenticación
            System.err.println("Error en el proceso de autenticación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Autenticación fallida: " + e.getMessage());
        }
    }

    // Listar todos los usuarios (solo administrador)
    @GetMapping("/todos")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> listarTodosLosUsuarios() {
        return usuarioService.listarTodosLosUsuarios();
    }

    // Editar usuario (incluyendo el rol)
    @PutMapping("/{id}")
    public Usuario editarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return usuarioService.editarUsuario(id, usuarioActualizado);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }
}

// Clase auxiliar para manejar la solicitud de login
class LoginRequest {
    private String email;
    private String password;

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
