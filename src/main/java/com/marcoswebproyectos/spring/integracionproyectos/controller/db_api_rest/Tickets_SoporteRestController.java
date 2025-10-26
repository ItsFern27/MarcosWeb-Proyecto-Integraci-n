package com.marcoswebproyectos.spring.integracionproyectos.controller.db_api_rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcoswebproyectos.spring.integracionproyectos.model.Tickets_Soporte;
import com.marcoswebproyectos.spring.integracionproyectos.repository.Tickets_SoporteRepository;

@RestController
public class Tickets_SoporteRestController {

    private final Tickets_SoporteRepository repo;

    public Tickets_SoporteRestController( Tickets_SoporteRepository repo ) {
        this.repo = repo;
    }

    @GetMapping("/tickets_soporte")
    public List<Tickets_Soporte> geTickets_Soporte() {
        return repo.findAll();
    }

}
