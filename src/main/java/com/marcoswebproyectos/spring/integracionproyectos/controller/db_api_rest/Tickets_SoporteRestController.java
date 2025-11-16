package com.marcoswebproyectos.spring.integracionproyectos.controller.db_api_rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcoswebproyectos.spring.integracionproyectos.model.Tecnologias;
import com.marcoswebproyectos.spring.integracionproyectos.repository.TecnologiasRepository;

@RestController
public class Tickets_SoporteRestController {

    private final TecnologiasRepository repo;

    public Tickets_SoporteRestController( TecnologiasRepository repo ) {
        this.repo = repo;
    }

    @GetMapping("/tickets_soporte")
    public List<Tecnologias> geTickets_Soporte() {
        return repo.findAll();
    }

}
