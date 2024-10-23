package com.JWTCurso.service.carrito;

import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Carrito;
import com.JWTCurso.model.ItemCarrito;
import com.JWTCurso.model.Producto;
import com.JWTCurso.repository.CarritoRepository;
import com.JWTCurso.repository.ItemCarritoRepository;
import com.JWTCurso.repository.ProductoRepository;
import com.JWTCurso.service.producto.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class ItemCarritoServiceImp implements ItemCarritoService {
    private final ItemCarritoRepository itemCarritoRepository;
    private final CarritoRepository carritoRepository;
    private final ProductoService productoService;
    private final CarritoService carritoService;
    private final ProductoRepository productoRepository;

    @Override
    public void agregarItemAlCarrito(Integer idCarrito, Integer idProducto, int cantidad) {
        Carrito carrito = carritoService.getCarritoById(idCarrito);
        Producto producto = productoService.getProductoById(idProducto);
        ItemCarrito itemCarrito = carrito.getItemsCarrito()
                .stream()
                .filter( item -> item.getProducto().getId().equals(idProducto))
                .findFirst().orElse(new ItemCarrito());
        if(itemCarrito.getId() == null) {
            itemCarrito.setId(carrito.getId());
            itemCarrito.setProducto(producto);
            itemCarrito.setCantidad(cantidad);
            itemCarrito.setPrecioUnidad(producto.getPrecio());
        }
        else{
            itemCarrito.setCantidad(itemCarrito.getCantidad() + cantidad);
        }
        itemCarrito.setPrecioTotal();
        carrito.addItemCarrito(itemCarrito);
        int nuevoStock = producto.getStock() - cantidad;
        producto.setStock(nuevoStock);
        productoRepository.save(producto);
        itemCarritoRepository.save(itemCarrito);
        carritoRepository.save(carrito);


    }

    @Override
    public void eliminarItemDelCarrito(Integer idCarrito, Integer idProducto) {
        Carrito carrito = carritoService.getCarritoById(idCarrito);
        ItemCarrito itemCarritoEliminar = getItem(idCarrito, idProducto);
        carrito.removeItemCarrito(itemCarritoEliminar);
        carritoRepository.save(carrito);

    }

    @Override
    public void updateItem(Integer idCarrito, Integer idProducto, int cantidad) {
        Carrito carrito = carritoService.getCarritoById(idCarrito);
        carrito.getItemsCarrito()
                .stream()
                .filter(item -> item.getProducto().getId().equals(idProducto))
                .findFirst()
                .ifPresent(item -> {
                    item.setCantidad(cantidad);
                    item.setPrecioUnidad(item.getProducto().getPrecio());
                    item.setPrecioTotal();
                });
        BigDecimal precioTotal = carrito.getItemsCarrito()
                .stream().map(ItemCarrito::getPrecioTotal)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        carrito.setPrecioTotal(precioTotal);
        carritoRepository.save(carrito);
    }

    @Override
    public ItemCarrito getItem(Integer idCarrito, Integer idProducto) {
        Carrito carrito = carritoService.getCarritoById(idCarrito);
        return carrito.getItemsCarrito()
                .stream()
                .filter(itemCarrito -> itemCarrito.getProducto().getId().equals(idProducto))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("No se encontro el producto"));
    }
}
