package com.proyecto.biblioteca.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.biblioteca.dto.PrestamoDTO;
import com.proyecto.biblioteca.models.EstadoLibro;
import com.proyecto.biblioteca.models.Libro;
import com.proyecto.biblioteca.models.Persona;
import com.proyecto.biblioteca.models.Prestamo;
import com.proyecto.biblioteca.repositories.LibroRepository;
import com.proyecto.biblioteca.repositories.PersonaRepository;
import com.proyecto.biblioteca.repositories.PrestamoRepository;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private PersonaRepository personaRepository;

    // Crear un nuevo préstamo
    public Prestamo crearPrestamo(PrestamoDTO prestamoDTO) {

        LocalDate fechaPrestamo = LocalDate.parse(prestamoDTO.getFechaPrestamo());
        LocalDate fechaDevolucion = LocalDate.parse(prestamoDTO.getFechaDevolucion());

        // Buscar el libro
        Libro libro = libroRepository.findById(prestamoDTO.getLibroId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Verificar si el libro está disponible
        if (libro.getEstado() != EstadoLibro.DISPONIBLE) {
            throw new RuntimeException("El libro no está disponible");
        }

        // Buscar la persona
        Persona persona = personaRepository.findById(prestamoDTO.getPersonaId())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        // Crear y configurar el nuevo préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setLibro(libro);
        prestamo.setPersona(persona);

        // Actualizar el estado del libro a PRESTADO
        libro.setEstado(EstadoLibro.PRESTADO);
        libroRepository.save(libro);

        // Guardar el préstamo
        return prestamoRepository.save(prestamo);
    }

    // Obtener todos los préstamos
    public List<Prestamo> obtenerTodos() {
        return prestamoRepository.findAll();
    }

    // Obtener un préstamo por ID
    public Prestamo obtenerPorId(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
    }

    // Finalizar un préstamo (devolver el libro)
    public Prestamo finalizarPrestamo(Long id) {
        Prestamo prestamo = obtenerPorId(id);

        // Actualizar el estado del libro a DISPONIBLE
        Libro libro = prestamo.getLibro();
        libro.setEstado(EstadoLibro.DISPONIBLE);
        libroRepository.save(libro);

        // (Opcional) Actualizar el préstamo como "finalizado"
        prestamo.setFechaDevolucion(LocalDate.now());
        return prestamoRepository.save(prestamo);
    }
}
