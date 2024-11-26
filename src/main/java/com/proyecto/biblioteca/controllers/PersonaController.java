package com.proyecto.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.biblioteca.models.Persona;
import com.proyecto.biblioteca.services.PersonaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/personas")
public class PersonaController {
    @Autowired
    private PersonaService personaService;

    @GetMapping
    public List<Persona> findPersonaAll() {
        return personaService.findPersonaAll();
    }

    @GetMapping("/{id}")
    public Persona findPersonaById(@PathVariable("id") Long id){
        return personaService.findPersonaById(id);
    }

    @PostMapping
    public ResponseEntity<Persona> savePersona(@RequestBody Persona persona){
        Persona nuevPersona = personaService.savePersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevPersona);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable("id") Long id, @RequestBody Persona datosActualizados) {
        Persona personaActualizada = personaService.updatePersonaById(id, datosActualizados);
        return ResponseEntity.ok(personaActualizada);
    }
    @DeleteMapping("/{id}")
    public void deletePersonaById(@PathVariable("id") Long id){
        personaService.deletePersonaById(id);
    }
}
