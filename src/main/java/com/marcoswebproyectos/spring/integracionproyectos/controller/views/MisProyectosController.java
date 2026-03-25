package com.marcoswebproyectos.spring.integracionproyectos.controller.views;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.marcoswebproyectos.spring.integracionproyectos.model.Miembros_Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;
import com.marcoswebproyectos.spring.integracionproyectos.repository.Miembros_ProyectosRepository;
import com.marcoswebproyectos.spring.integracionproyectos.repository.ProyectosRepository;
import com.marcoswebproyectos.spring.integracionproyectos.repository.UsuarioRepository;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MisProyectosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProyectosRepository proyectosRepository;

    @Autowired
    private Miembros_ProyectosRepository miembrosProyectosRepository;

    @GetMapping("/mis-proyectos")
    public String getMisProyectos(Principal principal, Model model) {

        // usuario logueado
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // proyectos usuario autor
        List<Proyectos> proyectosAutor = proyectosRepository.findByAutor(usuario);

        // proyectos usuario miembro
        List<Miembros_Proyectos> membresias = miembrosProyectosRepository.findAll()
                .stream()
                .filter(m -> m.getUsuario().getId().equals(usuario.getId()))
                .collect(Collectors.toList());

        List<Proyectos> proyectosMiembro = membresias.stream()
                .map(Miembros_Proyectos::getProyecto)
                .collect(Collectors.toList());

        // Mapa proyectoId -> rol del usuario en ese proyecto
        Map<Long, String> rolPorProyecto = membresias.stream()
                .collect(Collectors.toMap(
                        m -> m.getProyecto().getId(),
                        Miembros_Proyectos::getRol));

        List<Proyectos> proyectosTotales = Stream.concat(proyectosAutor.stream(), proyectosMiembro.stream())
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("proyectosAutor", proyectosAutor);
        model.addAttribute("proyectosMiembro", proyectosMiembro);
        model.addAttribute("rolPorProyecto", rolPorProyecto);
        model.addAttribute("proyectosTotales", proyectosTotales);
        model.addAttribute("usuario", usuario);

        log.debug("Dashboard cargado para el usuario: {}", usuario.getEmail());
        return "mis-proyectos";
    }
}

