package com.proyecto.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.biblioteca.models.Libro;
import com.proyecto.biblioteca.repositories.LibroRepository;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> findLibrosAll() {
        return libroRepository.findAll();
    }

    public Libro findLibroById(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    public Libro saveLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public Libro updateLibroById(Long id, Libro datosActualizados) {
        return libroRepository.findById(id)
                .map(libro -> {
                    libro.setTitulo(datosActualizados.getTitulo());
                    libro.setAutor(datosActualizados.getAutor());
                    libro.setAñoPublicacion(datosActualizados.getAñoPublicacion());
                    libro.setEstado(datosActualizados.getEstado());
                    return libroRepository.save(libro);
                })
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
    }

    public void deleteLibroById(Long id) {
        libroRepository.deleteById(id);
    }
}
