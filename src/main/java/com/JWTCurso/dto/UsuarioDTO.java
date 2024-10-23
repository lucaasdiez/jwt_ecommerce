package com.JWTCurso.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private List<OrdenDTO> ordenes;
    private CarritoDTO carrito;
}
