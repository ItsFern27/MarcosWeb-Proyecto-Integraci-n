package com.marcoswebproyectos.spring.integracionproyectos.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DbService {

    @Autowired
    private JdbcTemplate jdbcTemplate; // Conexión al servicio de consultas a la BD

    public Map<String, Object> obtenerConsultas() {
        Map<String, Object> resultado = new HashMap<>();

        try {
            List<Map<String, Object>> tablasSoloNombres = jdbcTemplate.queryForList(
                "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'"
            );
            resultado.put("tables", tablasSoloNombres);
        } catch (Exception e) {
            resultado.put("tables", e.getMessage());
        }

        try {
            List<Map<String, Object>> tablasSoloNombres = jdbcTemplate.queryForList(
                "SELECT * FROM usuarios"
            );
            resultado.put("usuarios", tablasSoloNombres);
        } catch (Exception e) {
            resultado.put("usuarios", e.getMessage());
        }

        try {
            List<Map<String, Object>> tablasSoloNombres = jdbcTemplate.queryForList(
                "SELECT * FROM proyectos"
            );
            resultado.put("proyectos", tablasSoloNombres);
        } catch (Exception e) {
            resultado.put("proyectos", e.getMessage());
        }
        return resultado;
    }   



}
