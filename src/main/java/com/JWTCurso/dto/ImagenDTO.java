package com.JWTCurso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagenDTO {
    private Integer id;
    private String nombre;
    private String imagenURL;
}
