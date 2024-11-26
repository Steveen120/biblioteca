package com.proyecto.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.biblioteca.models.Persona;
import com.proyecto.biblioteca.repositories.PersonaRepository;

@Service
public class PersonaService {
    @Autowired
    private PersonaRepository personaRepository;

    public List<Persona> findPersonaAll() {
        return personaRepository.findAll();
    }

    public Persona findPersonaById(Long id) {
        return personaRepository.findById(id).orElse(null);
    }

    public Persona savePersona(Persona persona) {
        return personaRepository.save(persona);
    }

    public Persona updatePersonaById(Long id, Persona datosActualizados) {
        // Se verifica si la persona existe
        return personaRepository.findById(id)
                .map(persona -> {
                    // Actualizamos los campos
                    persona.setNombre(datosActualizados.getNombre());
                    persona.setTelefono(datosActualizados.getTelefono());
                    persona.setEmail(datosActualizados.getEmail());

                    // Guardamos la persona actualizada
                    return personaRepository.save(persona);
                })
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + id));
    }

    public void deletePersonaById(Long id) {
        personaRepository.deleteById(id);
    }
}
