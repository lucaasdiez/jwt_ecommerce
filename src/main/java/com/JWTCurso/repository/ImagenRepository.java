package com.JWTCurso.repository;

import com.JWTCurso.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenRepository extends JpaRepository<Imagen, Integer> {
    List<Imagen> findByProductoId(Integer id);
}
