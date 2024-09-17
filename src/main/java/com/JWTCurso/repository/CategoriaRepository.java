package com.JWTCurso.repository;

import com.JWTCurso.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Categoria findByNombre(String nome);
    boolean existsByNombre(String nombre);
}
