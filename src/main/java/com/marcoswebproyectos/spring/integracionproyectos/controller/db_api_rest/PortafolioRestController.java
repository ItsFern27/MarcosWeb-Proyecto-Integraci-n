package com.marcoswebproyectos.spring.integracionproyectos.controller.db_api_rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcoswebproyectos.spring.integracionproyectos.model.Portafolio;
import com.marcoswebproyectos.spring.integracionproyectos.repository.PortafolioRepository;

@RestController
public class PortafolioRestController {

    private final PortafolioRepository repo;

    public PortafolioRestController( PortafolioRepository repo ) {
        this.repo = repo;
    }

    @GetMapping("/portafolio")
    public List<Portafolio> getPortafolio() {
        return repo.findAll();
    }

}
