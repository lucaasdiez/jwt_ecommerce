package com.JWTCurso.repository;

import com.JWTCurso.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoriaNombre(String categoria);
    List<Producto> findByMarca(String marca);
    List<Producto> findByCategoriaNombreAndMarca(String categoria, String marca);
    List<Producto> findByNombre(String nombre);
    List<Producto> findByNombreAndMarca(String nombre, String marca);
    Long countProductoByMarcaAndNombre(String marca, String nombre);
}
