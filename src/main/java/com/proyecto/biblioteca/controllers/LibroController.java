package com.proyecto.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.proyecto.biblioteca.models.Libro;
import com.proyecto.biblioteca.models.Usuario;
import com.proyecto.biblioteca.services.LibroService;
import com.proyecto.biblioteca.services.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;
    @Autowired
    private UsuarioService usuarioService;

    // Crear un libro (solo administrador)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Libro crearLibro(@RequestBody Libro libro) {
        return libroService.crearLibro(libro);
    }

    // Listar todos los libros disponibles
    @GetMapping("/disponibles")
    public List<Libro> listarLibrosDisponibles() {
        return libroService.listarLibrosDisponibles();
    }

    // Listar todos los libros prestados
    @GetMapping("/prestados")
    public List<Libro> listarLibrosPrestados() {
        return libroService.listarLibrosPrestados();
    }

    // Editar un libro (solo administrador)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Libro editarLibro(@PathVariable("id") Long id, @RequestBody Libro libroActualizado) {
        return libroService.editarLibro(id, libroActualizado);
    }

    // Eliminar un libro (solo administrador)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarLibro(@PathVariable("id") Long id) {
        libroService.eliminarLibro(id);
    }

    // Préstamo de un libro
    @PostMapping("/{id}/prestar")
    public Libro prestarLibro(@PathVariable("id") Long id) {
        Long usuarioId = obtenerIdUsuarioActual();
        Usuario usuario = usuarioService.buscarPorId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return libroService.prestarLibro(id, usuario);
    }

    // Devolución de un libro
    @PostMapping("/{id}/devolver")
    public Libro devolverLibro(@PathVariable("id") Long id) {
        return libroService.devolverLibro(id);
    }

    private Long obtenerIdUsuarioActual() {
        // Obtiene el principal desde el SecurityContext
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
        // Verifica si el principal es un String (usualmente el nombre de usuario o email)
        if (principal instanceof String) {
            String email = (String) principal;
            return usuarioService.buscarPorEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                    .getId();
        }
    
        // Lanza excepción si el usuario no está autenticado o el principal no es válido
        throw new RuntimeException("Usuario no autenticado");
    }
    
}
