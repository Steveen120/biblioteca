package com.proyecto.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.biblioteca.models.Rol;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
