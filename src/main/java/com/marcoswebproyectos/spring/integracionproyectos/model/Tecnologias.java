package com.marcoswebproyectos.spring.integracionproyectos.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tecnologias")
@Getter
@Setter
public class Tecnologias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String color;

    // Muchos tickets pertenecen a un usuario
    @OneToMany
    @JoinColumn(name = "tecnologia")
    private List<Proyectos_Tecnologias> proyectos;

}
