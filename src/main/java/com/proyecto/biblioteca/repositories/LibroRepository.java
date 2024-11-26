package com.proyecto.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.biblioteca.models.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long>{

}
