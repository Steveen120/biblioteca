package com.proyecto.biblioteca.dtos;

import com.proyecto.biblioteca.models.Rol;

public class RolDTO {
    private Long id;
    private String nombre;

    public RolDTO(Rol rol) {
        this.id = rol.getId();
        this.nombre = rol.getNombre();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
