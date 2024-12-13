package com.proyecto.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.proyecto.biblioteca.configurations.JwtService;
import com.proyecto.biblioteca.dtos.UsuarioDTO;
import com.proyecto.biblioteca.models.Usuario;
import com.proyecto.biblioteca.services.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

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
    public UsuarioDTO login(@RequestBody UsuarioDTO usuarioDTO) {
        // Buscar usuario por email
        Usuario usuario = usuarioService.buscarPorEmail(usuarioDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
        // Validar la contraseña
        if (!passwordEncoder.matches(usuarioDTO.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
    
        // Generar el token JWT
        String token = jwtService.generarToken(usuario.getEmail());
    
        // Devolver el DTO con el token incluido
        UsuarioDTO respuesta = new UsuarioDTO(usuario);
        respuesta.setToken(token);
        return respuesta;
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



