package com.JWTCurso.service.orden;

import com.JWTCurso.dto.OrdenDTO;
import com.JWTCurso.enums.OrdenEstado;
import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Carrito;
import com.JWTCurso.model.ItemOrden;
import com.JWTCurso.model.Orden;
import com.JWTCurso.model.Producto;
import com.JWTCurso.repository.OrdenRepository;
import com.JWTCurso.repository.ProductoRepository;
import com.JWTCurso.service.carrito.CarritoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenServiceImp implements OrdenService {
    private final OrdenRepository ordenRepository;
    private final ProductoRepository productoRepository;
    private final CarritoService carritoService;
    private final ModelMapper modelMapper;

    @Override
    public Orden crearOrden(Integer usuarioId) {
        Carrito carrito = carritoService.getCarritoByUsuarioId(usuarioId);
        Orden orden = crearOrden(carrito);
        List<ItemOrden> itemsOrden = crearItemOrden(orden, carrito);
        orden.setItemOrdenes(new HashSet<>(itemsOrden));
        orden.setPrecioTotal(calcularPrecioTotal(itemsOrden));
        Orden ordenSaved = ordenRepository.save(orden);
        carritoService.limparCarrito(carrito.getId());
        return ordenSaved;
    }
    private Orden crearOrden(Carrito carrito){
        Orden ordenNuevo = new Orden();
        ordenNuevo.setUsuario(carrito.getUsuario());
        ordenNuevo.setFecha(LocalDate.now());
        ordenNuevo.setOrdenEstado(OrdenEstado.ESPERA);
        return ordenNuevo;
    }

    private List<ItemOrden> crearItemOrden(Orden orden, Carrito carrito) {
        return carrito.getItemsCarrito().stream()
                .map(itemCarrito -> {
                    Producto producto = itemCarrito.getProducto();
                    producto.setStock(producto.getStock() - itemCarrito.getCantidad());
                    productoRepository.save(producto);
                    return new ItemOrden(
                            itemCarrito.getCantidad(),
                            itemCarrito.getPrecioUnidad(),
                            orden,
                            producto);
                }).toList();
    }

    private BigDecimal calcularPrecioTotal(List<ItemOrden> itemOrdenes) {
        return itemOrdenes.stream()
                .map(item -> item.getPrecio()
                        .multiply(new BigDecimal(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    public OrdenDTO getOrdenById(Integer id) {
        return ordenRepository.findById(id)
                .map(this::convertirOrdenADTO)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada"));
    }

    public List<OrdenDTO> getOrdenesByUsuario(Integer usuarioId) {
        return ordenRepository.findById(usuarioId)
                .stream()
                .map(this::convertirOrdenADTO)
                .toList();
    }
    @Override
    public OrdenDTO convertirOrdenADTO(Orden orden) {
        return modelMapper.map(orden, OrdenDTO.class);
    }
}
