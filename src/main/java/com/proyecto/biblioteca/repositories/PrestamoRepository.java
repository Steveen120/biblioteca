package com.proyecto.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.biblioteca.models.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

}
