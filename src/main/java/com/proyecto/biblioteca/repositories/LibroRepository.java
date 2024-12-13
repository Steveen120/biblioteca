package com.proyecto.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.biblioteca.models.Libro;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByPrestado(boolean prestado); // Libros prestados o disponibles

    List<Libro> findByUsuarioId(Long usuarioId); // Libros prestados a un usuario específico
}
