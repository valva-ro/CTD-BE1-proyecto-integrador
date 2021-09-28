package com.valva.proyectointegrador.service.impl.auth;

import com.valva.proyectointegrador.exceptions.BadRequestException;
import com.valva.proyectointegrador.persistence.entities.auth.Usuario;
import com.valva.proyectointegrador.persistence.entities.auth.UsuarioRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final UsuarioService usuarioService;

    @Autowired
    public DataLoader(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void run(ApplicationArguments args) throws BadRequestException {
        usuarioService.crear(new Usuario(123456789, "admin", "admin@gmail.com", "admin", UsuarioRoles.ADMIN));
        usuarioService.crear(new Usuario(123456788, "user", "user@gmail.com", "user", UsuarioRoles.USER));
    }
}
