package com.proyecto.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.proyecto.biblioteca.models.Prestamo;
import com.proyecto.biblioteca.services.PrestamoService;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Prestamo> listarPrestamosPorUsuario(@PathVariable("usuarioId") Long usuarioId) {
        return prestamoService.listarPrestamosPorUsuario(usuarioId);
    }

    @GetMapping("/libro/{libroId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Prestamo> listarHistorialDeLibro(@PathVariable("libroId") Long libroId) {
        return prestamoService.listarHistorialDeLibro(libroId);
    }
}
