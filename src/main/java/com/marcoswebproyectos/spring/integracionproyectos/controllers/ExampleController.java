package com.marcoswebproyectos.spring.integracionproyectos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ExampleController {

    @GetMapping("/detalles_info")
    public String showMainPage(Model model){
        model.addAttribute("Titulo", "Servidor en lìnea");
        model.addAttribute("Servidor", "Nose");
        model.addAttribute("Ip", "192.168.1.1");
        
        return "detalles_info";
        
    }

}
