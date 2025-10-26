package com.marcoswebproyectos.spring.integracionproyectos.controller.db_api_rest;

import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;
import com.marcoswebproyectos.spring.integracionproyectos.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/usuarios/{id}")
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return repo.findById(id)
                .map(usuario -> {
                    usuario.setNombre(usuarioActualizado.getNombre());
                    usuario.setEmail(usuarioActualizado.getEmail());
                    usuario.setPassword(usuarioActualizado.getPassword());
                    return repo.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    
    @DeleteMapping("/usuarios/{id}")
    public String deleteUsuario(@PathVariable Long id) {
        repo.deleteById(id);
        return "Usuario eliminado con éxito (ID: " + id + ")";
    }
}
