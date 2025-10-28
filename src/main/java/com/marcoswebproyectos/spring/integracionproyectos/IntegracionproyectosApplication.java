package com.marcoswebproyectos.spring.integracionproyectos;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.marcoswebproyectos.spring.integracionproyectos.model.Usuario;
import com.marcoswebproyectos.spring.integracionproyectos.repository.UsuarioRepository;

@SpringBootApplication
public class IntegracionproyectosApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegracionproyectosApplication.class, args);
	}
	@Bean
	CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// 1) Crear admin si no existe
			String adminEmail = "admin@mail.com";
			if (usuarioRepository.findByEmail(adminEmail).isEmpty()) {
				Usuario admin = new Usuario();
				admin.setNombre("Admin");
				admin.setEmail(adminEmail);
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setRol("admin");
				usuarioRepository.save(admin);
				System.out.println("Usuario admin creado: " + adminEmail + " / admin123");
			}

			// 2) Hashear contraseñas existentes
			List<Usuario> all = usuarioRepository.findAll();
			for (Usuario u : all) {
				String pw = u.getPassword();
				if (pw == null)
					continue;
				// BCrypt
				if (!(pw.startsWith("$2a$") || pw.startsWith("$2b$") || pw.startsWith("$2y$"))) {
					String hashed = passwordEncoder.encode(pw);
					u.setPassword(hashed);
					usuarioRepository.save(u);
					System.out.println("Hasheada contraseña para usuario: " + u.getEmail());
				}
			}
		};
	}
}
