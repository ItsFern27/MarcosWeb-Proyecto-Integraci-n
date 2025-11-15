package com.marcoswebproyectos.spring.integracionproyectos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;

import java.util.List;

public interface ProyectosRepository extends JpaRepository<Proyectos, Long> {
    List<Proyectos> findByAutor(Usuario autor);
}
