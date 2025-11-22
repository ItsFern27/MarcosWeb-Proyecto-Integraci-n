package com.marcoswebproyectos.spring.integracionproyectos.controller;

import com.marcoswebproyectos.spring.integracionproyectos.model.Tecnologias;
import com.marcoswebproyectos.spring.integracionproyectos.repository.TecnologiasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private TecnologiasRepository tecnologiasRepository;

    @ModelAttribute("todasLasTecnologias")
    public List<Tecnologias> todasLasTecnologias() {
        return tecnologiasRepository.findAll();
    }
}
