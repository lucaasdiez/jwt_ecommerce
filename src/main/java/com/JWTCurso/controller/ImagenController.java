package com.JWTCurso.controller;

import com.JWTCurso.dto.ImagenDTO;
import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Imagen;
import com.JWTCurso.response.ApiResponse;
import com.JWTCurso.service.imagen.ImagenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/imagenes")
public class ImagenController {
    private final ImagenService imagenService;

    @PostMapping("/subir")
    public ResponseEntity<ApiResponse> saveImagenes(@RequestParam List<MultipartFile> imagenes, @RequestParam Integer idProducto) {
        try {
            List<ImagenDTO> imagenDTOS = imagenService.saveImagen(imagenes, idProducto);
            return ResponseEntity.ok(new ApiResponse("OK", imagenDTOS));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/imagen/descargar/{idImagen}")
    @Transactional
    public ResponseEntity<Resource> descargarImagen(@PathVariable Integer idImagen) throws SQLException {
        Imagen imagen = imagenService.getImagen(idImagen);
        ByteArrayResource byteArrayResource = new ByteArrayResource(imagen.getImagen().getBytes(1, (int) imagen.getImagen().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(imagen.getArchivoTipo()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +imagen.getArchivoNombre()+ "\"")
                .body(byteArrayResource);
    }

    @PutMapping("/imagen/{idImagen}/update")
    public ResponseEntity<ApiResponse> updateImagen(@PathVariable Integer idImagen, @RequestParam MultipartFile file){
        try {
            Imagen imagen = imagenService.getImagen(idImagen);
            if(imagen != null){
                imagenService.updateImagen(idImagen, file);
                return ResponseEntity.ok(new ApiResponse("OK", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR", INTERNAL_SERVER_ERROR));
    }


    @DeleteMapping("/imagen/{idImagen}/delete")
    public ResponseEntity<ApiResponse> deleteImagen(@PathVariable Integer idImagen) {
        try {
            Imagen imagen= imagenService.getImagen(idImagen);
            if (imagen != null){
                imagenService.deleteImagen(idImagen);
                return ResponseEntity.ok(new ApiResponse("ELIMINADO",null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR", INTERNAL_SERVER_ERROR));
    }
}
