package com.marcoswebproyectos.spring.integracionproyectos.controller;

import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;
import com.marcoswebproyectos.spring.integracionproyectos.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- Página de Login ---
    @GetMapping("/login")
    public String loginPage(Model model) {

        // Por request, spring puede acceder al usuario quien la hizo de esta forma:
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // condición de null por seguridad (caso raro donde auth sea null)
        // isAuthenticated da true o false de acuerdo a la sesion del servidor
        // AnonymousAuthenticationToken es la clase que se usa cuando no está logueado
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }


        model.addAttribute("Titulo", "Iniciar Sesión");
        return "login";
    }

    // --- Página de Registro ---
    @GetMapping("/register")
    public String registerPage(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        model.addAttribute("Titulo", "Registrarse");
        return "registro";
    }

    // --- Procesar Registro ---
    @PostMapping("/register")
    public String processRegister(@RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {

        model.addAttribute("Titulo", "Registrarse");

        // Validar nombre
        if (nombre == null || nombre.trim().isEmpty()) {
            model.addAttribute("error", "El nombre no puede estar vacío");
            return "registro";
        }
        if (nombre.trim().length() > 100) {
            model.addAttribute("error", "El nombre no puede superar los 100 caracteres");
            return "registro";
        }

        // Validar formato de email básico
        if (email == null || !email.matches("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$")) {
            model.addAttribute("error", "El correo electrónico no es válido");
            return "registro";
        }

        // Validar contraseña
        if (password == null || password.length() < 8) {
            model.addAttribute("error", "La contraseña debe tener al menos 8 caracteres");
            return "registro";
        }

        // Verificar si ya existe el usuario
        if (usuarioRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "El correo ya está registrado");
            return "registro";
        }

        // Crear y guardar usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre.trim());
        nuevoUsuario.setEmail(email.toLowerCase().trim());
        nuevoUsuario.setPassword(passwordEncoder.encode(password));
        nuevoUsuario.setRol("USER"); // Por defecto un rol básico

        usuarioRepository.save(nuevoUsuario);
        log.info("Nuevo usuario registrado: {}", email);

        // Redirigir al login con mensaje de éxito
        model.addAttribute("success", "Cuenta creada con éxito. Ahora inicia sesión.");
        return "login";
    }
}

