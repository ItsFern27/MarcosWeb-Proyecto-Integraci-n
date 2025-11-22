package com.marcoswebproyectos.spring.integracionproyectos.repository;

import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProyectosRepository extends JpaRepository<Proyectos, Long> {
    List<Proyectos> findByAutor(Usuario autor);

    @Query("SELECT p FROM Proyectos p LEFT JOIN FETCH p.tecnologias")
    List<Proyectos> findAllWithTecnologias();

    // NOTA: Esta consulta requiere que la extensión `unaccent` esté habilitada en la base de datos de PostgreSQL.
    // Ejecutar: CREATE EXTENSION IF NOT EXISTS unaccent;
    @Query(value = "SELECT DISTINCT p.* FROM proyectos p " +
                   "LEFT JOIN proyectos_tecnologia pt ON p.id = pt.proyecto_id " +
                   "LEFT JOIN tecnologias t ON pt.tecnologia_id = t.id WHERE " +
                   "(:titulo IS NULL OR :titulo = '' OR unaccent(LOWER(p.titulo)) LIKE unaccent(LOWER(CONCAT('%', :titulo, '%')))) AND " +
                   "(:tecnologiaId IS NULL OR t.id = :tecnologiaId)",
           nativeQuery = true)
    List<Proyectos> search(@Param("titulo") String titulo, @Param("tecnologiaId") Long tecnologiaId);
}
