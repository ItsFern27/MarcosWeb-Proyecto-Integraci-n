package com.marcoswebproyectos.spring.integracionproyectos.controller.db_api_rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcoswebproyectos.spring.integracionproyectos.model.Miembros_Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.repository.Miembros_ProyectosRepository;

@RestController
public class Miembros_ProyectosRestController {

    private final Miembros_ProyectosRepository repo;

    public Miembros_ProyectosRestController( Miembros_ProyectosRepository repo ) {
        this.repo = repo;
    }

    @GetMapping("/miembros_proyectos")
    public List<Miembros_Proyectos> getMiembros_Proyectos() {
        return repo.findAll();
    }

}
