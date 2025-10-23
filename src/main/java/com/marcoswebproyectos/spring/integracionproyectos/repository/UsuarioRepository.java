package com.marcoswebproyectos.spring.integracionproyectos.repository;

import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

