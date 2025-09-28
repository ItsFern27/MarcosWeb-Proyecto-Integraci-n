package com.marcoswebproyectos.spring.integracionproyectos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
    
    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        
        // Adición para gestión dinámica de datos de los proyectos
        model.addAttribute("Titulo1", "Dashboard Analytics");
        model.addAttribute("Titulo2", "App de Fitness");
        model.addAttribute("Titulo3", "Sistema de Seguridad");
        model.addAttribute("Titulo4", "E-commerce Platform");
        model.addAttribute("Titulo5", "ML Predictor");
        model.addAttribute("Titulo6", "Red Social Creativa");
        return "index";
    }
    
    
}
