package com.proyecto.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.biblioteca.dto.PrestamoDTO;
import com.proyecto.biblioteca.models.Prestamo;
import com.proyecto.biblioteca.services.PrestamoService;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    // Crear un nuevo préstamo
    @PostMapping
    public ResponseEntity<Prestamo> crearPrestamo(@RequestBody PrestamoDTO prestamoDTO) {
        Prestamo nuevoPrestamo = prestamoService.crearPrestamo(prestamoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPrestamo);
    }

    // Obtener todos los préstamos
    @GetMapping
    public ResponseEntity<List<Prestamo>> obtenerTodos() {
        List<Prestamo> prestamos = prestamoService.obtenerTodos();
        return ResponseEntity.ok(prestamos);
    }

    // Obtener un préstamo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPorId(@PathVariable("id") Long id) {
        Prestamo prestamo = prestamoService.obtenerPorId(id);
        return ResponseEntity.ok(prestamo);
    }

    // Finalizar un préstamo (devolver libro)
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Prestamo> finalizarPrestamo(@PathVariable Long id) {
        Prestamo prestamoFinalizado = prestamoService.finalizarPrestamo(id);
        return ResponseEntity.ok(prestamoFinalizado);
    }
}


