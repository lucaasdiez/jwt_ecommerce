package com.JWTCurso.service.categoria;

import com.JWTCurso.exeptions.AlreadyExistExeption;
import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Categoria;
import com.JWTCurso.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImp implements CategoriaService{
    private final CategoriaRepository categoriaRepository;


    @Override
    public Categoria getCategoriaById(Integer id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));
    }

    @Override
    public Categoria getCategoriaByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }

    @Override
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria addCategoria(Categoria categoria) {
        return Optional.of(categoria).filter(c -> !categoriaRepository.existsByNombre(c.getNombre()))
                .map(categoriaRepository :: save)
                .orElseThrow(() -> new AlreadyExistExeption(categoria.getNombre()+ " ya existe"));
    }

    @Override
    public Categoria updateCategoria(Categoria categoria, Integer id) {
        return Optional.ofNullable(getCategoriaById(id)).map(viejaCategoria ->{
            viejaCategoria.setNombre(categoria.getNombre());
            return categoriaRepository.save(viejaCategoria);
        }).orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));
    }

    @Override
    public void deleteCategoria(Integer id) {
        categoriaRepository.findById(id)
                .ifPresentOrElse(categoriaRepository::delete, () ->{
                    throw new ResourceNotFoundException("Categoria no encontrada");
                });
    }
}
