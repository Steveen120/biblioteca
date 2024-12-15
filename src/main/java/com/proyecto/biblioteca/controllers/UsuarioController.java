package com.proyecto.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.proyecto.biblioteca.configurations.JwtService;
import com.proyecto.biblioteca.dtos.UsuarioDTO;
import com.proyecto.biblioteca.models.Rol;
import com.proyecto.biblioteca.models.Usuario;
import com.proyecto.biblioteca.repositories.RolRepository;
import com.proyecto.biblioteca.services.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/registro")
    public UsuarioDTO registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        // Validación para asegurar que el email no esté en uso
        if (usuarioService.buscarPorEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Convertir el DTO a un objeto Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());

        // Obtener el rol por nombre desde la base de datos
        Rol rol = rolRepository.findByNombre(usuarioDTO.getRolNombre())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + usuarioDTO.getRolNombre()));
        usuario.setRol(rol);

        // Registrar el usuario usando el servicio
        Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);

        // Convertir el usuario registrado de vuelta a DTO
        UsuarioDTO usuarioRespuestaDTO = new UsuarioDTO(usuarioRegistrado);
        return usuarioRespuestaDTO;
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
    public Usuario editarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuarioActualizado) {
        return usuarioService.editarUsuario(id, usuarioActualizado);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarUsuario(@PathVariable("id") Long id) {
        usuarioService.eliminarUsuario(id);
    }
}
