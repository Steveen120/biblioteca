package com.proyecto.biblioteca.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    
    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Usuario> usuarios;

}