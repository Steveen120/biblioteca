package com.proyecto.biblioteca.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

}