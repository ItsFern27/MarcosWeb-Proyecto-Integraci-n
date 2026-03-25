package com.marcoswebproyectos.spring.integracionproyectos.controller.views;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.marcoswebproyectos.spring.integracionproyectos.model.Miembros_Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos;
import com.marcoswebproyectos.spring.integracionproyectos.model.Proyectos_Tecnologias;
import com.marcoswebproyectos.spring.integracionproyectos.model.Tecnologias;
import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;
import com.marcoswebproyectos.spring.integracionproyectos.repository.Miembros_ProyectosRepository;
import com.marcoswebproyectos.spring.integracionproyectos.repository.ProyectosRepository;
import com.marcoswebproyectos.spring.integracionproyectos.repository.Proyectos_TecnologiasRepository;
import com.marcoswebproyectos.spring.integracionproyectos.repository.TecnologiasRepository;
import com.marcoswebproyectos.spring.integracionproyectos.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/proyecto")
@RequiredArgsConstructor
public class DetalleProyectoController {
    
    private final ProyectosRepository proyectosRepository;
    private final Miembros_ProyectosRepository miembrosRepository;
    private final UsuarioRepository usuarioRepository;
    private final TecnologiasRepository tecnologiasRepository;
    private final Proyectos_TecnologiasRepository proyectosTecnologiasRepository;

    @GetMapping("/{id}")
    public String mostrarDetalleProyecto(@PathVariable Long id, Model model, Principal principal) {
        Proyectos proyecto = proyectosRepository.findById(id)
                                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));
        
        boolean esMiembro = false;
        boolean esAutor = false;

        if (principal != null) {
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(principal.getName());
            if (usuarioOpt.isPresent()) {
                Usuario usuarioActual = usuarioOpt.get();
                esAutor = proyecto.getAutor().getId().equals(usuarioActual.getId());
                esMiembro = miembrosRepository.existsByProyectoAndUsuario(proyecto, usuarioActual);
            }
        }

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("esMiembro", esMiembro);
        model.addAttribute("esAutor", esAutor);
        model.addAttribute("color", "#D9401E");
        return "detalle-proyecto";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Proyectos proyecto = proyectosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));

        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!proyecto.getAutor().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para editar este proyecto");
        }

        if ("Finalizado".equals(proyecto.getEstado())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede editar un proyecto finalizado");
        }

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("allTecnologias", tecnologiasRepository.findAll());
        return "editar-proyecto";
    }

    @PostMapping("/{id}/editar")
    public String guardarEdicionProyecto(@PathVariable Long id,
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("duracion") String duracion,
            @RequestParam("urlImagen") String urlImagen,
            @RequestParam("tecnologias") List<Long> tecnologiasIds,
            Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        Proyectos proyecto = proyectosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));

        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!proyecto.getAutor().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para editar este proyecto");
        }

        if ("Finalizado".equals(proyecto.getEstado())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede editar un proyecto finalizado");
        }

        // Actualizar campos del proyecto
        proyecto.setTitulo(titulo != null ? titulo.trim() : proyecto.getTitulo());
        proyecto.setDescripcion(descripcion != null ? descripcion.trim() : proyecto.getDescripcion());
        proyecto.setDuracion(duracion != null ? duracion.trim() : proyecto.getDuracion());
        proyecto.setUrlImagen(urlImagen != null ? urlImagen.trim() : proyecto.getUrlImagen());

        // Actualizar tecnologías: eliminar las anteriores y añadir las nuevas
        proyectosTecnologiasRepository.deleteAll(proyecto.getTecnologias());
        proyectosRepository.save(proyecto);

        for (Long tecId : tecnologiasIds) {
            tecnologiasRepository.findById(tecId).ifPresent(tec -> {
                Proyectos_Tecnologias pt = new Proyectos_Tecnologias();
                pt.setProyecto(proyecto);
                pt.setTecnologia(tec);
                proyectosTecnologiasRepository.save(pt);
            });
        }

        log.info("Proyecto {} actualizado por el usuario {}", id, principal.getName());
        return "redirect:/proyecto/" + id;
    }

    @PostMapping("/{id}/unirse")
    public String unirseAlProyecto(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Proyectos proyecto = proyectosRepository.findById(id)
                                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));
        
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                                           .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Verificar si el usuario ya es miembro o autor
        boolean yaEsMiembro = miembrosRepository.existsByProyectoAndUsuario(proyecto, usuario);
        boolean esAutor = proyecto.getAutor().getId().equals(usuario.getId());

        if (yaEsMiembro || esAutor) {
            // Opcional: añadir un mensaje de error/notificación
            return "redirect:/proyecto/" + id;
        }

        Miembros_Proyectos nuevoMiembro = new Miembros_Proyectos();
        nuevoMiembro.setProyecto(proyecto);
        nuevoMiembro.setUsuario(usuario);
        nuevoMiembro.setRol("Miembro"); // Rol por defecto

        miembrosRepository.save(nuevoMiembro);

        return "redirect:/proyecto/" + id;
    }

    @PostMapping("/{id}/abandonar")
    public String abandonarProyecto(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Proyectos proyecto = proyectosRepository.findById(id)
                                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));
        
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                                           .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Un autor no puede abandonar su propio proyecto como si fuera un miembro
        if (proyecto.getAutor().getId().equals(usuario.getId())) {
            // Opcional: añadir un mensaje de error/notificación
            return "redirect:/proyecto/" + id;
        }

        Optional<Miembros_Proyectos> membresia = miembrosRepository.findByProyectoAndUsuario(proyecto, usuario);
        if (membresia.isPresent()) {
            miembrosRepository.delete(membresia.get());
        }

        return "redirect:/proyecto/" + id;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarProyecto(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Proyectos proyecto = proyectosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));

        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!proyecto.getAutor().getId().equals(usuario.getId())) {
            // Si no es el autor, no tiene permiso para eliminar
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para eliminar este proyecto");
        }

        proyectosRepository.delete(proyecto);

        return "redirect:/mis-proyectos";
    }

    @PostMapping("/{id}/finalizar")
    public String finalizarProyecto(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Proyectos proyecto = proyectosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));

        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!proyecto.getAutor().getId().equals(usuario.getId())) {
            // Si no es el autor, no tiene permiso para finalizar
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para finalizar este proyecto");
        }

        proyecto.setEstado("Finalizado");
        proyectosRepository.save(proyecto);

        return "redirect:/proyecto/" + id;
    }

}

