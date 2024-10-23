package com.JWTCurso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoDTO {
    private Integer id;
    private BigDecimal precioTotal;
    private Set<ItemCarritoDTO> itemsCarrito;
}
