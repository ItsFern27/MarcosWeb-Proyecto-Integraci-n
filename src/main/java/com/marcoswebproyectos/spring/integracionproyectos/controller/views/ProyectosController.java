package com.marcoswebproyectos.spring.integracionproyectos.controller.views;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;
import com.marcoswebproyectos.spring.integracionproyectos.repository.ProyectosRepository;
import com.marcoswebproyectos.spring.integracionproyectos.service.CustomUserDetailsService;

@Controller
public class ProyectosController {

    @Autowired
    private ProyectosRepository proyectosRepository;

    @Autowired
    private CustomUserDetailsService userService;

    // pagina principal de proyectos
    @GetMapping("/mis-proyectos")
    public String giveProys() {
        return "mis-proyectos";
    }

    // formulario para crear nuevo proyecto
    @GetMapping("/mis-proyectos/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("proyecto", new Proyectos());
        return "crear-proyecto";
    }

    // procesar formulario de creacion
    @PostMapping("/mis-proyectos/crear")
    public String crearProyecto(Proyectos proyecto, Principal principal) {

        // Obtener al usuario autenticado
        Usuario autor = userService.getUsuarioByEmail(principal.getName());
        proyecto.setAutor(autor);

        // Por defecto
        proyecto.setEstado("En progreso");

        proyectosRepository.save(proyecto);

        return "redirect:/mis-proyectos";
    }

}
