package com.marcoswebproyectos.spring.integracionproyectos.config;

import com.marcoswebproyectos.spring.integracionproyectos.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    // Inyección por constructor
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // --- Configuración principal de seguridad ---
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactiva CSRF solo para desarrollo
                .csrf(AbstractHttpConfigurer::disable)

                // Define qué rutas son públicas y cuáles requieren autenticación
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index", "/login", "/register",
                                "/css/**", "/js/**", "/img/**", "/img2/**", "/proyectos/buscar")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Solo estas rutas requieren login
                        .requestMatchers("/mis-proyectos/**", "/proyecto/**").authenticated()
                        .anyRequest().permitAll())

                // Configura el formulario de inicio de sesión
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true) // redirige al index tras login
                        .permitAll())

                // Configura el cierre de sesión
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/") // redirige al index después de cerrar sesión
                        .permitAll())

                // Permite pruebas con Postman o endpoints API
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
    
    // Proveedor de autenticación (usa tu servicio y BCrypt) ---
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Password Encoder (BCrypt) ---
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager ---
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
