package com.proyecto.biblioteca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.biblioteca.models.Libro;
import com.proyecto.biblioteca.models.Prestamo;
import com.proyecto.biblioteca.models.Usuario;
import com.proyecto.biblioteca.repositories.PrestamoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    public Prestamo registrarPrestamo(Libro libro, Usuario usuario) {
        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDate.now());
        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> listarPrestamosPorUsuario(Long usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId);
    }

    public List<Prestamo> listarHistorialDeLibro(Long libroId) {
        return prestamoRepository.findByLibroId(libroId);
    }
}

