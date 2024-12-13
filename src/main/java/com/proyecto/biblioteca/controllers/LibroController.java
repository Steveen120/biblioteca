package com.proyecto.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PreAuthorize("hasRole('ADMIN')")
    public List<Libro> listarLibrosPrestados() {
        return libroService.listarLibrosPrestados();
    }

    // Editar un libro (solo administrador)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Libro editarLibro(@PathVariable Long id, @RequestBody Libro libroActualizado) {
        return libroService.editarLibro(id, libroActualizado);
    }

    // Eliminar un libro (solo administrador)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibro(id);
    }

    // Préstamo de un libro
    @PostMapping("/{id}/prestar")
    public Libro prestarLibro(@PathVariable Long id) {
        Long usuarioId = obtenerIdUsuarioActual();
        Usuario usuario = usuarioService.buscarPorId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return libroService.prestarLibro(id, usuario);
    }

    // Devolución de un libro
    @PostMapping("/{id}/devolver")
    public Libro devolverLibro(@PathVariable Long id) {
        return libroService.devolverLibro(id);
    }

    private Long obtenerIdUsuarioActual() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return usuarioService.buscarPorEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                    .getId();
        }
        throw new RuntimeException("Usuario no autenticado");
    }
}
