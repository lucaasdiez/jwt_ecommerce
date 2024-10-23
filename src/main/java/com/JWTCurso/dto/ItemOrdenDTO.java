package com.JWTCurso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrdenDTO {
    private Integer id;
    private String nombre;
    private int cantidad;
    private BigDecimal precio;

}
