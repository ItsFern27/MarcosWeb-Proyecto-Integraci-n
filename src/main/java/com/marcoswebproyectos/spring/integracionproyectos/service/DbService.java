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
            resultado.put("Nombres de las tablas", tablasSoloNombres);
        } catch (Exception e) {
            resultado.put("Nombres de las tablas", e.getMessage());
        }

        try {
            List<Map<String, Object>> tablasCompleta = jdbcTemplate.queryForList(
                "SELECT rol FROM usuarios"
            );
            resultado.put("consulta2", tablasCompleta);
        } catch (Exception e) {
            resultado.put("consulta2_error", e.getMessage());
        }

        return resultado;
    }   



}
