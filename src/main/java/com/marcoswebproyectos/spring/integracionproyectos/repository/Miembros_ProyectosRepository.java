package com.marcoswebproyectos.spring.integracionproyectos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.marcoswebproyectos.spring.integracionproyectos.model.Miembros_Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;

public interface Miembros_ProyectosRepository extends JpaRepository<Miembros_Proyectos, Long> {

    boolean existsByProyectoAndUsuario(Proyectos proyecto, Usuario usuario);

    Optional<Miembros_Proyectos> findByProyectoAndUsuario(Proyectos proyecto, Usuario usuario);

}
