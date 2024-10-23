package com.JWTCurso.repository;

import com.JWTCurso.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    Carrito findCarritoByUsuarioId(Integer id);


}
