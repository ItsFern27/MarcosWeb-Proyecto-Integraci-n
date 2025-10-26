package com.marcoswebproyectos.spring.integracionproyectos.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String email;
    private String password;

    private String rol;

    // Un usuario puede ser autor de varios proyectos
    @OneToMany(mappedBy = "autor_id") // EL mappedBy dirige aL atributo de La cLase de abajo
    private List<Proyectos> proyectosAutor;

    // Un usuario puede ser miembro de varios proyectos
    @OneToMany(mappedBy = "usuario_id")
    private List<Miembros_Proyectos> membresias;

    // Un usuario puede tener varios elementos en su portafolio
    @OneToMany(mappedBy = "usuario")
    private List<Portafolio> portafolio;

    // Un usuario puede crear varios tickets de soporte
    @OneToMany(mappedBy = "usuario")
    private List<Tickets_Soporte> tickets;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
