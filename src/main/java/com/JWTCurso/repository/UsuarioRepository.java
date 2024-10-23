package com.JWTCurso.repository;

import com.JWTCurso.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByEmail(String email);

    Usuario findByEmail(String email);
}
