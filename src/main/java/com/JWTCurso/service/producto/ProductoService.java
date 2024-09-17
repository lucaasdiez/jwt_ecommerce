package com.JWTCurso.service.producto;

import com.JWTCurso.dto.ProductoDTO;
import com.JWTCurso.model.Producto;


import java.util.List;


public interface ProductoService {

    Producto getProductoById(Integer id);
    Producto addProducto(ProductoDTO producto);
    void deleteProductoById(Integer id);
    Producto updateProducto(ProductoDTO productoDTO, Integer id);
    List<Producto> getAllProductos();
    List<Producto> getProductoByCategoria(String categoria);
    List<Producto> getProductoByMarca(String marca);
    List<Producto> getProductoByCategoriaAndMarca(String categoria, String marca);
    List<Producto> getProductoByNombre(String nombre);
    List<Producto> getProductoByNombreAndMarca(String nombre, String marca);
    Long contarProductosPorMarcaAndNombre(String marca, String nombre);

    List<ProductoDTO> getProductosDTO(List<Producto> productos);
    ProductoDTO convertirProductoToProductoDTO(Producto producto);

}
