package com.marcoswebproyectos.spring.integracionproyectos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProyectosController {
    
    @GetMapping("/mis-proyectos")
    public String giveProys() {
        return "mis-proyectos";
    }
        
}
