package com.JWTCurso.dto;


import com.JWTCurso.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {

    private Integer id;
    private String nombre;
    private String marca;
    private BigDecimal precio;
    private int stock;
    private String descripcion;
    private Categoria categoria;
    private List<ImagenDTO> imagenes;


}
