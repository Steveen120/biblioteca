package com.proyecto.biblioteca.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    private boolean prestado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; 
}