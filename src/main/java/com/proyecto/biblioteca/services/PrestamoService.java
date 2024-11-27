package com.proyecto.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.biblioteca.models.Prestamo;
import com.proyecto.biblioteca.repositories.PrestamoRepository;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    // Obtener todos los préstamos
    public List<Prestamo> findPrestamosAll() {
        return prestamoRepository.findAll();
    }

    // Obtener un préstamo por ID
    public Prestamo findPrestamoById(Long id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    // Crear un nuevo préstamo
    public Prestamo savePrestamo(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    // Actualizar un préstamo existente
    public Prestamo updatePrestamoById(Long id, Prestamo datosActualizados) {
        return prestamoRepository.findById(id)
                .map(prestamo -> {
                    // Actualizar campos del préstamo
                    prestamo.setFechaPrestamo(datosActualizados.getFechaPrestamo());
                    prestamo.setFechaDevolucion(datosActualizados.getFechaDevolucion());
                    prestamo.setLibro(datosActualizados.getLibro());
                    prestamo.setPersona(datosActualizados.getPersona());
                    return prestamoRepository.save(prestamo);
                })
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
    }

    // Eliminar un préstamo por ID
    public boolean deletePrestamoById(Long id) {
        if (prestamoRepository.existsById(id)) {
            prestamoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
