package com.JWTCurso.service.carrito;

import com.JWTCurso.model.Carrito;
import com.JWTCurso.model.Usuario;

import java.math.BigDecimal;

public interface CarritoService {
    Carrito getCarritoById(Integer id);
    void limparCarrito(Integer id);
    BigDecimal getPrecioTotal(Integer id);
    Carrito inicializarNewCarrito(Usuario usuario);
    Carrito getCarritoByUsuarioId(Integer usuarioId);
}
