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
@Table(name = "portafolio")
@Getter
@Setter
public class Portafolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    private String url;

    // Muchos portafolios pertenecen a un usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
