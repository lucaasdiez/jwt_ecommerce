package com.JWTCurso.controller;

import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Carrito;
import com.JWTCurso.response.ApiResponse;
import com.JWTCurso.service.carrito.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carritos")
public class CarritoController {
    private final CarritoService carritoService;

    @GetMapping("/{id}/carrito")
    public ResponseEntity<ApiResponse> getCarrito(@PathVariable Integer id) {
        try {
            Carrito carrito= carritoService.getCarritoById(id);
            return ResponseEntity.ok(new ApiResponse("Carrito", carrito));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}/limpiar")
    public ResponseEntity<ApiResponse> limpiarCarrito(@PathVariable Integer id) {
        try {
            carritoService.limparCarrito(id);
            return ResponseEntity.ok(new ApiResponse("Carrito", null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/{id}/carrito/precio-total")
    public ResponseEntity<ApiResponse> getPrecioTotal(@PathVariable Integer id) {
        try {
            BigDecimal precioTotal = carritoService.getPrecioTotal(id);
            return ResponseEntity.ok(new ApiResponse("Precio Total", precioTotal));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
