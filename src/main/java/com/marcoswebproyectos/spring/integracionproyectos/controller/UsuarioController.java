package com.marcoswebproyectos.spring.integracionproyectos.controller;

import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;
import com.marcoswebproyectos.spring.integracionproyectos.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioController {

    private final UsuarioRepository repo;

    public UsuarioController(UsuarioRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios() {
        return repo.findAll();
    }

    @PostMapping("/usuarios")
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return repo.save(usuario);
    }
}
