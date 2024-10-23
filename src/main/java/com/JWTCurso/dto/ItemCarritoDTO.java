package com.JWTCurso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarritoDTO {
    private Integer id;
    private int cantidad;
    private BigDecimal precioUnidad;
    private BigDecimal precioTotal;
    private ProductoDTO producto;
    private CarritoDTO carrito;
}
