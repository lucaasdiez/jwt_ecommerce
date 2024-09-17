package com.JWTCurso.service.categoria;

import com.JWTCurso.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoriaService {
    Categoria getCategoriaById(Integer id);
    Categoria getCategoriaByNombre(String nome);
    List<Categoria> getAllCategorias();
    Categoria addCategoria(Categoria categoria);
    Categoria updateCategoria(Categoria categoria, Integer id);
    void deleteCategoria(Integer id);

}
