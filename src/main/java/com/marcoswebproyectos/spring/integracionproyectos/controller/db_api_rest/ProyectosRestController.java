package com.marcoswebproyectos.spring.integracionproyectos.controller.db_api_rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.repository.ProyectosRepository;

@RestController
public class ProyectosRestController {

    private final ProyectosRepository repo;

    public ProyectosRestController( ProyectosRepository repo ) {
        this.repo = repo;
    }

    @GetMapping("/proyectos")
    public List<Proyectos> getProyectos() {
        return repo.findAll();
    }

}
