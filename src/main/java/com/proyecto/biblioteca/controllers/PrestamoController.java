package com.proyecto.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.biblioteca.models.Prestamo;
import com.proyecto.biblioteca.services.PrestamoService;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    // Obtener todos los préstamos
    @GetMapping
    public List<Prestamo> findPrestamosAll() {
        return prestamoService.findPrestamosAll();
    }

    // Obtener un préstamo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> findPrestamoById(@PathVariable("id") Long id) {
        Prestamo prestamo = prestamoService.findPrestamoById(id);
        if (prestamo != null) {
            return ResponseEntity.ok(prestamo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Crear un nuevo préstamo
    @PostMapping
    public ResponseEntity<Prestamo> savePrestamo(@RequestBody Prestamo prestamo) {
        Prestamo nuevoPrestamo = prestamoService.savePrestamo(prestamo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPrestamo);
    }

    // Actualizar un préstamo existente
    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> updatePrestamo(@PathVariable("id") Long id, @RequestBody Prestamo datosActualizados) {
        Prestamo prestamoActualizado = prestamoService.updatePrestamoById(id, datosActualizados);
        if (prestamoActualizado != null) {
            return ResponseEntity.ok(prestamoActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Eliminar un préstamo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrestamo(@PathVariable("id") Long id) {
        if (prestamoService.deletePrestamoById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
