package com.proyecto.biblioteca;

import com.proyecto.biblioteca.models.Rol;
import com.proyecto.biblioteca.repositories.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RolRepository rolRepository;

    public DataLoader(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (rolRepository.findByNombre("ROLE_ADMIN").isEmpty()) {
            Rol adminRol = new Rol();
            adminRol.setNombre("ROLE_ADMIN");
            rolRepository.save(adminRol);
        }

        if (rolRepository.findByNombre("ROLE_USER").isEmpty()) {
            Rol userRol = new Rol();
            userRol.setNombre("ROLE_USER");
            rolRepository.save(userRol);
        }
    }
}
