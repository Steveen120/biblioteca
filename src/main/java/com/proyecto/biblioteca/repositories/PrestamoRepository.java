package com.proyecto.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.biblioteca.models.Prestamo;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByUsuarioId(Long usuarioId); // Préstamos por usuario
    List<Prestamo> findByLibroId(Long libroId); // Historial de un libro específico
    Optional<Prestamo> findByLibroIdAndFechaDevolucionIsNull(Long libroId);
}

