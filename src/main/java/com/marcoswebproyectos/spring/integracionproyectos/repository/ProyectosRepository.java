package com.marcoswebproyectos.spring.integracionproyectos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos;

public interface ProyectosRepository extends JpaRepository<Proyectos, Long> {

}
