package com.JWTCurso.service.carrito;

import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Carrito;
import com.JWTCurso.model.Usuario;
import com.JWTCurso.repository.CarritoRepository;
import com.JWTCurso.repository.ItemCarritoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CarritoServiceImp implements CarritoService {
    private final CarritoRepository carritoRepository;
    private final ItemCarritoRepository ItemCarritoRepository;

    @Override
    public Carrito getCarritoById(Integer id) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));
        BigDecimal precioTotal = carrito.getPrecioTotal();
        carrito.setPrecioTotal(precioTotal);
        return carrito;
    }

    @Transactional
    @Override
    public void limparCarrito(Integer id) {
        Carrito carrito = getCarritoById(id);
        ItemCarritoRepository.deleteAllByCarritoId(id);
        carrito.getItemsCarrito().clear();
        carritoRepository.deleteById(id);

    }

    @Override
    public BigDecimal getPrecioTotal(Integer id) {
        Carrito carrito = getCarritoById(id);
        return carrito.getPrecioTotal();
    }

    @Override
    public Carrito inicializarNewCarrito(Usuario usuario) {
        return Optional.ofNullable(getCarritoByUsuarioId(usuario.getId()))
                .orElseGet(() -> {
                    Carrito carrito = new Carrito();
                    carrito.setUsuario(usuario);
                    return carritoRepository.save(carrito);
                });
    }

    @Override
    public Carrito getCarritoByUsuarioId(Integer usuarioId) {
        return carritoRepository.findCarritoByUsuarioId(usuarioId);
    }


}
