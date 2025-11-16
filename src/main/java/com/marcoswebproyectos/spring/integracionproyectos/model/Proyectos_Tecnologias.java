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
@Table(name = "proyectos_tecnologia")
@Getter
@Setter
public class Proyectos_Tecnologias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tecnologia_id")
    private Tecnologias tecnologia;

    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyectos proyecto;

}
