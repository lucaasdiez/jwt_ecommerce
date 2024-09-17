package com.JWTCurso.service.categoria;

import com.JWTCurso.model.Categoria;


import java.util.List;


public interface CategoriaService {
    Categoria getCategoriaById(Integer id);
    Categoria getCategoriaByNombre(String nome);
    List<Categoria> getAllCategorias();
    Categoria addCategoria(Categoria categoria);
    Categoria updateCategoria(Categoria categoria, Integer id);
    void deleteCategoria(Integer id);

}
