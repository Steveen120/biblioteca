    package com.proyecto.biblioteca.repositories;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import com.proyecto.biblioteca.models.Persona;

    @Repository
    public interface PersonaRepository extends JpaRepository<Persona, Long> {

    }
