package com.JWTCurso.service.imagen;

import com.JWTCurso.dto.ImagenDTO;
import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Imagen;
import com.JWTCurso.model.Producto;
import com.JWTCurso.repository.ImagenRepository;
import com.JWTCurso.service.producto.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagenServiceImp implements ImagenService {
    private final ImagenRepository imagenRepository;
    private final ProductoService productoService;

    @Override
    public Imagen getImagen(Integer id) {
        return imagenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imagen no encontrada con id:"+ id));
    }

    @Override
    public void deleteImagen(Integer id) {
        imagenRepository.findById(id).ifPresentOrElse(imagenRepository::delete, () -> {
            throw new ResourceNotFoundException("Imagen no encontrada con id:"+ id);
        });
    }

    @Override
    public List<ImagenDTO> saveImagen(List<MultipartFile> files, Integer idProducto) {
        Producto producto = productoService.getProductoById(idProducto);

        List<ImagenDTO> savedImagenDTO = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Imagen imagen = new Imagen();
                imagen.setArchivoNombre(file.getOriginalFilename());
                imagen.setArchivoTipo(file.getContentType());
                imagen.setImagen(new SerialBlob(file.getBytes()));
                imagen.setProducto(producto);

                String buildDescargaURL = "/api/v1/imagenes/imagen/download/";
                String descargaURL = buildDescargaURL + imagen.getId();
                imagen.setDescargaURL(descargaURL);
                Imagen saveImagen = imagenRepository.save(imagen);

                saveImagen.setDescargaURL(buildDescargaURL + saveImagen.getId());
                imagenRepository.save(saveImagen);

                ImagenDTO imagenDTO = new ImagenDTO();
                imagenDTO.setImagenURL(saveImagen.getDescargaURL());
                imagenDTO.setId(saveImagen.getId());
                imagenDTO.setNombre(saveImagen.getArchivoNombre());
                savedImagenDTO.add(imagenDTO);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImagenDTO;
    }

    @Override
    public void updateImagen(Integer id, MultipartFile file) {
        Imagen imagen = getImagen(id);
        try {
            imagen.setArchivoNombre(file.getOriginalFilename());
            imagen.setArchivoTipo(file.getContentType());
            imagen.setImagen(new SerialBlob(file.getBytes()));
            imagenRepository.save(imagen);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
