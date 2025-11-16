package com.marcoswebproyectos.spring.integracionproyectos.controller.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.repository.ProyectosRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/proyecto")
@RequiredArgsConstructor
public class DetalleProyectoController {
    
    private final ProyectosRepository proyectosRepository;

    @GetMapping("/{id}")
    public String mostrarDetalleProyecto(@PathVariable Long id, Model model) {
        Proyectos proyecto = proyectosRepository.findById(id)
                                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));
        model.addAttribute("proyecto", proyecto);
        return "detalle-proyecto";
    }

}
