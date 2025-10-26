package com.marcoswebproyectos.spring.integracionproyectos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "miembros_proyecto")
@Getter
@Setter
public class Miembros_Proyectos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rol;

    // Muchos miembros pertenecen a un proyecto
    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyectos proyecto;

    // Muchos miembros están asociados a un usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario_id;

}
