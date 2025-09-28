package com.marcoswebproyectos.spring.integracionproyectos.controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/api")
public class ExampleRestController {
    
    @GetMapping(path = "/detalles_info2")
    public Map<String, Object> detalles_info2(){
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Titulo", "Servidor en lìnea");
        respuesta.put("Servidor", "Nose");
        respuesta.put("Ip", "192.168.1.1");
        
        return respuesta;
        
    }

}
