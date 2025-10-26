package com.marcoswebproyectos.spring.integracionproyectos.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcoswebproyectos.spring.integracionproyectos.service.DbService;

@RestController
@RequestMapping("/admin")
public class DbController {

    @Autowired
    private DbService sqlService;
    
    @GetMapping("/db")
    public Map<String, Object> listarUsuarios() {
        return sqlService.obtenerConsultas();
    }

}
