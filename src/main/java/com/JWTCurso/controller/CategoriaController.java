package com.JWTCurso.controller;

import com.JWTCurso.exeptions.AlreadyExistExeption;
import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Categoria;
import com.JWTCurso.response.ApiResponse;
import com.JWTCurso.service.categoria.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categoria")
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategorias(){
        try {
            List<Categoria> categorias = categoriaService.getAllCategorias();
            return ResponseEntity.ok(new ApiResponse("Categorias", categorias));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addCategoria(@RequestBody Categoria categoria){
        try {
            Categoria categoriaNueva = categoriaService.addCategoria(categoria);
            return ResponseEntity.ok(new ApiResponse("Categoria", categoriaNueva));
        } catch (AlreadyExistExeption e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/categoria/{id}/categoria")
    public ResponseEntity<ApiResponse> getCategoriaById(@PathVariable Integer id){
        try {
            Categoria categoria = categoriaService.getCategoriaById(id);
            return ResponseEntity.ok(new ApiResponse("ENCONTRADA", categoria));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/categoria/{nombre}/categoria")
    public ResponseEntity<ApiResponse> getCategoriaByNombre(@PathVariable String nombre){
        try {
            Categoria categoria = categoriaService.getCategoriaByNombre(nombre);
            return ResponseEntity.ok(new ApiResponse("ENCONTRADA", categoria));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @DeleteMapping("/categoria/{id}/categoria")
    public ResponseEntity<ApiResponse> deleteCategoria(@PathVariable Integer id){
        try {
            categoriaService.deleteCategoria(id);
            return ResponseEntity.ok(new ApiResponse("ELIMINADA", null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/categoria/{id}/categoria")
    public ResponseEntity<ApiResponse> updateCategoria(@RequestBody Categoria categoria, @PathVariable Integer id){
        try {
            Categoria updateCategoria = categoriaService.updateCategoria(categoria, id);
            return ResponseEntity.ok(new ApiResponse("ACTUALIZADA", updateCategoria));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
