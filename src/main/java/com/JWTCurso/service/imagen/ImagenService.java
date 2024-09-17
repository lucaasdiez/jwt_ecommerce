package com.JWTCurso.service.imagen;

import com.JWTCurso.dto.ImagenDTO;
import com.JWTCurso.model.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImagenService {
    Imagen getImagen(Integer id);
    void deleteImagen(Integer id);
    List<ImagenDTO> saveImagen(List<MultipartFile> imagenes, Integer idProducto);
    void updateImagen(Integer id, MultipartFile file);

}
