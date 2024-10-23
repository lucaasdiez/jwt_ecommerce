package com.JWTCurso.data;

import com.JWTCurso.model.Rol;
import com.JWTCurso.model.Usuario;
import com.JWTCurso.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class Datainitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent( ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROL_ADMIN", "ROL_USUARIO");
        crearDefaultUsuarioIfNotExists();
        crearDefaultRolesIfNotExists(defaultRoles);
        crearDefaultAdminIfNotExists();
    }

    private void crearDefaultAdminIfNotExists() {
        boolean adminExist = rolRepository.findByNombre("ROL_ADMIN").isPresent();
        if (adminExist) {
            Rol admin = rolRepository.findByNombre("ROL_ADMIN").get();
            for(int i=1; i<=2; i++) {
                String defaultEmail = "admin"+i+"@gmail.com";
                if (usuarioRepository.existsByEmail(defaultEmail)) {
                    continue;
                }
                Usuario usuario = new Usuario();
                usuario.setNombre("Admin");
                usuario.setApellido("Admin"+ i);
                usuario.setEmail(defaultEmail);
                usuario.setPassword(passwordEncoder.encode("123456"));
                usuario.setRoles(Set.of(admin));
                usuarioRepository.save(usuario);
                System.out.println("Usuario Administrador: " + i + "creado correctamente");
            }
        }
    }


    private void crearDefaultUsuarioIfNotExists() {
        boolean adminExist = rolRepository.findByNombre("ROL_ADMIN").isPresent();
        if (adminExist) {
            Rol admin = rolRepository.findByNombre("ROL_ADMIN").get();
            for(int i=1; i<=5; i++) {
                String defaultEmail = "sam"+i+"@gmail.com";
                if (usuarioRepository.existsByEmail(defaultEmail)) {
                    continue;
                }
                Usuario usuario = new Usuario();
                usuario.setNombre("Usuario");
                usuario.setApellido("Usuario"+ i);
                usuario.setEmail(defaultEmail);
                usuario.setPassword(passwordEncoder.encode("123456"));
                usuario.setRoles(Set.of(admin));
                usuarioRepository.save(usuario);
                System.out.println("Usuario: " + i + "creado correctamente");
            }
        }
    }

    private void crearDefaultRolesIfNotExists(Set<String> defaultRoles) {
        defaultRoles.stream()
                .filter(rol -> rolRepository.findByNombre(rol).isEmpty())
                .map(Rol::new).forEach(rolRepository::save);
    }
}
