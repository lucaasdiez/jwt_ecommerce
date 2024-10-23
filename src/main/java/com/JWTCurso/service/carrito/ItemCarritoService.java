package com.JWTCurso.service.carrito;

import com.JWTCurso.model.ItemCarrito;

public interface ItemCarritoService {
    void agregarItemAlCarrito(Integer idCarrito, Integer idProducto, int cantidad);
    void eliminarItemDelCarrito(Integer idCarrito, Integer idProducto);
    void updateItem(Integer idCarrito, Integer idProducto, int cantidad);

    ItemCarrito getItem(Integer idCarrito, Integer idProducto);

}
