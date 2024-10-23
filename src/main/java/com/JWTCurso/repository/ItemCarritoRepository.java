package com.JWTCurso.repository;

import com.JWTCurso.model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Integer> {
    void deleteAllByCarritoId(Integer id);
}
