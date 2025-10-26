package com.marcoswebproyectos.spring.integracionproyectos.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "proyectos")
@Getter
@Setter
public class Proyectos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    private String estado;
    private String duracion;

    // Muchos proyectos pueden pertenecer a un solo autor
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor_id;

    // Un proyecto puede tener muchos miembros
    @OneToMany(mappedBy = "proyecto")
    private List<Miembros_Proyectos> miembros;

}
