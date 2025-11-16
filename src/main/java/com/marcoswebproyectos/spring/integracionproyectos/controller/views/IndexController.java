package com.marcoswebproyectos.spring.integracionproyectos.controller.views;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.repository.ProyectosRepository;


@Controller
public class IndexController {

    @Autowired
    private ProyectosRepository proyectosRepository;
    
    @GetMapping({"/", "/index"})
    public String getIndex(Principal principal, Model model) {
        
        List<Proyectos> proyectos = proyectosRepository.findAll();
        model.addAttribute("proyectos", proyectos);

        // model.addAttribute("prueba1", proyectos.get(0).getMiembros().get(0).getUsuario().getNombre());

        return "index";
    }
    
    
}
