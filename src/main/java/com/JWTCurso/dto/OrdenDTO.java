package com.JWTCurso.dto;

import com.JWTCurso.enums.OrdenEstado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenDTO {
    private Integer id;
    private LocalDate fecha;
    private BigDecimal precioTotal;
    private OrdenEstado ordenEstado;
    private Set<ItemOrdenDTO> itemOrdenes;
    private UsuarioDTO usuario;
    private String status;
}
