package com.JWTCurso.controller;

import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Carrito;
import com.JWTCurso.model.Usuario;
import com.JWTCurso.response.ApiResponse;
import com.JWTCurso.service.carrito.CarritoService;
import com.JWTCurso.service.carrito.ItemCarritoService;
import com.JWTCurso.service.usuario.UsuarioService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carritoItems")
public class ItemCarritoController {
    private final ItemCarritoService itemCarritoService;
    private final UsuarioService usuarioService;
    private final CarritoService carritoService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemACarrito(@RequestParam Integer idProducto,
                                                       @RequestParam int cantidad) {

        try {
            Usuario usuario = usuarioService.getUsuarioAutenticado();
            Carrito carrito = carritoService.inicializarNewCarrito(usuario);
            itemCarritoService.agregarItemAlCarrito(carrito.getId(), idProducto, cantidad);
            return ResponseEntity.ok(new ApiResponse("Item Agregado", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }catch (JwtException e){
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/carrito/{idCarrito}/item/{idItem}/eliminar")
    public ResponseEntity<ApiResponse> deleteItemACarrito(@PathVariable Integer idCarrito, @PathVariable Integer idItem) {
        try {
            itemCarritoService.eliminarItemDelCarrito(idCarrito, idItem);
            return ResponseEntity.ok(new ApiResponse("Item Eliminado", null));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/carrito/{idCarrito}/producto/{idProducto}/update")
    public ResponseEntity<ApiResponse> updateItemCantidad(@PathVariable Integer idCarrito,
                                                          @PathVariable Integer idProducto,
                                                          @RequestParam int cantidad){
        try {
            itemCarritoService.updateItem(idCarrito, idProducto, cantidad);
            return ResponseEntity.ok(new ApiResponse("Item Actualizado", null));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
}
