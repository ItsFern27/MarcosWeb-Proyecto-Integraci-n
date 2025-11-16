package com.marcoswebproyectos.spring.integracionproyectos.service;

import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;
import com.marcoswebproyectos.spring.integracionproyectos.repository.UsuarioRepository;
import com.marcoswebproyectos.spring.integracionproyectos.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        // String role = usuario.getRol() != null ? usuario.getRol().toUpperCase() : "USER";
        // if (!role.startsWith("ROLE_")) {
        //     role = "ROLE_" + role;
        // }

        return new CustomUserDetails(usuario);
    }

    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElse(null);
    }
}
