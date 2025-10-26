package com.marcoswebproyectos.spring.integracionproyectos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets_soporte")
public class Tickets_Soporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String asunto;
    private String mensaje;
    private String estado;

    // Muchos tickets pertenecen a un usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
