package com.marcoswebproyectos.spring.integracionproyectos.controller;

import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;
import com.marcoswebproyectos.spring.integracionproyectos.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- Página de Login ---
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("Titulo", "Iniciar Sesión");
        return "login";
    }

    // --- Página de Registro ---
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("Titulo", "Registrarse");
        return "registro";
    }

    // --- Procesar Registro ---
    @PostMapping("/register")
    public String processRegister(@RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {

        // Verificar si ya existe el usuario
        if (usuarioRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "El correo ya está registrado");
            model.addAttribute("Titulo", "Registrarse");
            return "registro";
        }

        // Crear y guardar usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(passwordEncoder.encode(password));
        nuevoUsuario.setRol("USER"); // Por defecto un rol básico

        usuarioRepository.save(nuevoUsuario);

        // Redirigir al login con mensaje de éxito
        model.addAttribute("success", "Cuenta creada con éxito. Ahora inicia sesión.");
        return "login";
    }
}
