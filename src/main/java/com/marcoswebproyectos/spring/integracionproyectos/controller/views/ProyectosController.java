package com.marcoswebproyectos.spring.integracionproyectos.controller.views;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos_Tecnologias;
import com.marcoswebproyectos.spring.integracionproyectos.model.Tecnologias;
import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;
import com.marcoswebproyectos.spring.integracionproyectos.repository.ProyectosRepository;
import com.marcoswebproyectos.spring.integracionproyectos.repository.Proyectos_TecnologiasRepository;
import com.marcoswebproyectos.spring.integracionproyectos.repository.TecnologiasRepository;
import com.marcoswebproyectos.spring.integracionproyectos.service.CustomUserDetailsService;

@Controller
public class ProyectosController {

    @Autowired
    private ProyectosRepository proyectosRepository;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private TecnologiasRepository tecnologiasRepository;

    @Autowired
    private Proyectos_TecnologiasRepository proyectosTecnologiasRepository;

    // pagina principal de proyectos
    @GetMapping("/mis-proyectos")
    public String giveProys() {
        return "mis-proyectos";
    }

    // formulario para crear nuevo proyecto
    @GetMapping("/mis-proyectos/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("proyecto", new Proyectos());

        // Obtener todas las tecnologias desded el repository para la selección en el front
        model.addAttribute("allTecnologias", tecnologiasRepository.findAll());
        return "crear-proyecto";
    }

    // procesar formulario de creacion
    @PostMapping("/mis-proyectos/crear")
    public String crearProyecto(Proyectos proyecto, @RequestParam("tecnologias") List<Long> tecnologiasIds, Principal principal) {

        // Obtener al usuario autenticado
        Usuario autor = userService.getUsuarioByEmail(principal.getName());
        proyecto.setAutor(autor);

        // Por defecto
        proyecto.setEstado("En progreso");
        Proyectos proyectoGuardado = proyectosRepository.save(proyecto);

        // Luego de crear, se insertan las tecnologias seleccionadas en Proyectos_Tecnologias con proyectoGuardado (que actuaria como el proyecto_id)
        for (Long tecId : tecnologiasIds) {
            Tecnologias tec = tecnologiasRepository.findById(tecId).orElse(null);
            if (tec != null) {
                Proyectos_Tecnologias pt = new Proyectos_Tecnologias();
                pt.setProyecto(proyectoGuardado);
                pt.setTecnologia(tec);
                proyectosTecnologiasRepository.save(pt);
            }
        }

        return "redirect:/mis-proyectos";
    }

}
