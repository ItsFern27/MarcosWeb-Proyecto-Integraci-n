package com.marcoswebproyectos.spring.integracionproyectos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.marcoswebproyectos.spring.integracionproyectos.model.Tecnologias;

public interface TecnologiasRepository extends JpaRepository<Tecnologias, Long> {
}
