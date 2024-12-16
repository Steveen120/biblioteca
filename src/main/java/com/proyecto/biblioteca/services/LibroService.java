package com.proyecto.biblioteca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.biblioteca.models.Libro;
import com.proyecto.biblioteca.models.Usuario;
import com.proyecto.biblioteca.repositories.LibroRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    // Crear un libro
    public Libro crearLibro(Libro libro) {
        libro.setPrestado(false); // Siempre comienza como disponible
        libro.setUsuario(null);
        return libroRepository.save(libro);
    }

    // Buscar un libro por ID
    public Optional<Libro> buscarPorId(Long id) {
        return libroRepository.findById(id);
    }

    // Editar un libro
    public Libro editarLibro(Long id, Libro libroActualizado) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        libro.setTitulo(libroActualizado.getTitulo());
        libro.setAutor(libroActualizado.getAutor());
        return libroRepository.save(libro);
    }

    // Eliminar un libro
    public void eliminarLibro(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro no encontrado");
        }
        libroRepository.deleteById(id);
    }

    // Listar libros disponibles
    public List<Libro> listarLibrosDisponibles() {
        return libroRepository.findByPrestado(false);
    }

    // Listar libros prestados
    public List<Libro> listarLibrosPrestados() {
        return libroRepository.findByPrestado(true);
    }

    // Listar libros prestados a un usuario específico
    public List<Libro> listarLibrosPrestadosPorUsuario(Long usuarioId) {
        return libroRepository.findByUsuarioId(usuarioId);
    }
    // Metodo para prestar un Libro
    @Transactional
    public Libro prestarLibro(Long libroId, Usuario usuario) {
        // Buscar el libro en la base de datos
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    
        // Verificar si el libro ya está prestado
        if (libro.isPrestado()) {
            throw new RuntimeException("El libro ya está prestado");
        }
    
        // Asignar el libro al usuario y marcarlo como prestado
        libro.setUsuario(usuario);
        libro.setPrestado(true);
    
        // Guardar los cambios y retornar el libro actualizado
        return libroRepository.save(libro);
    }
    

    // Método para devolver libro
    @Transactional
    public Libro devolverLibro(Long libroId) {
        // Buscar el libro en la base de datos
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Verificar si el libro está prestado
        if (!libro.isPrestado()) {
            throw new RuntimeException("El libro no está prestado");
        }

        // Devolver el libro (desasignar al usuario)
        libro.setUsuario(null);
        libro.setPrestado(false);
        return libroRepository.save(libro);
    }
}
