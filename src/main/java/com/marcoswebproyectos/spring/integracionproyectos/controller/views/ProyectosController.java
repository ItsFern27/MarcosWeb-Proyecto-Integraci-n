package com.marcoswebproyectos.spring.integracionproyectos.controller.views;

import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos_Tecnologias;
import com.marcoswebproyectos.spring.integracionproyectos.model.Tecnologias;
import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;
import com.marcoswebproyectos.spring.integracionproyectos.repository.ProyectosRepository;
import com.marcoswebproyectos.spring.integracionproyectos.repository.Proyectos_TecnologiasRepository;
import com.marcoswebproyectos.spring.integracionproyectos.repository.TecnologiasRepository;
import com.marcoswebproyectos.spring.integracionproyectos.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Slf4j
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

    @GetMapping("/proyectos/buscar")
    public String buscarProyectos(@RequestParam(value = "titulo", required = false) String titulo,
                                @RequestParam(value = "tecnologiaId", required = false) Long tecnologiaId,
                                Model model) {
        List<Proyectos> proyectos = proyectosRepository.search(titulo, tecnologiaId);
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("titulo", titulo);
        model.addAttribute("tecnologiaId", tecnologiaId);
        return "search-results";
    }

    // formulario para crear nuevo proyecto
    @GetMapping("/mis-proyectos/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("proyecto", new Proyectos());
        model.addAttribute("allTecnologias", tecnologiasRepository.findAll());
        return "crear-proyecto";
    }

    // procesar formulario de creacion
    @PostMapping("/mis-proyectos/crear")
    public String crearProyecto(Proyectos proyecto, @RequestParam("tecnologias") List<Long> tecnologiasIds,
            Principal principal) {
        Usuario autor = userService.getUsuarioByEmail(principal.getName());
        proyecto.setAutor(autor);
        proyecto.setEstado("En progreso");
        Proyectos proyectoGuardado = proyectosRepository.save(proyecto);
        log.info("Proyecto '{}' creado por el usuario {}", proyecto.getTitulo(), principal.getName());

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
