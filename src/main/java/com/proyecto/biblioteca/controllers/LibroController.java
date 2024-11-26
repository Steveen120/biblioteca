package com.proyecto.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.biblioteca.models.Libro;
import com.proyecto.biblioteca.services.LibroService;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
@Autowired
private LibroService libroService;
@GetMapping
public List<Libro> findLibrosAll(){
    return libroService.findLibrosAll();
}
@GetMapping("/{id}")
public Libro findLibroById(@PathVariable("id") Long id){
    return libroService.findLibroById(id);
}
@PostMapping
public ResponseEntity<Libro> saveLibro(@RequestBody Libro libro){
    Libro nuevolibro = libroService.saveLibro(libro);
    return ResponseEntity.status(HttpStatus.CREATED).body(nuevolibro);
}
@PutMapping("/{id}")
public ResponseEntity<Libro> updateLibro(@PathVariable("id") Long id, @RequestBody Libro datosActualizados){
    Libro libroActualizado = libroService.updateLibroById(id, datosActualizados);
    return ResponseEntity.ok(libroActualizado);
}
@DeleteMapping("/{id}")
public void deleteLibro(@PathVariable("id") Long id){
    libroService.deleteLibroById(id);
}
}
