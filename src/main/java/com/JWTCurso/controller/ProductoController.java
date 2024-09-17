package com.JWTCurso.controller;

import com.JWTCurso.dto.ProductoDTO;
import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Producto;
import com.JWTCurso.response.ApiResponse;
import com.JWTCurso.service.producto.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/producto")
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProductos() {
        List<Producto> productos = productoService.getAllProductos();
        List<ProductoDTO> productoDTOS = productoService.getProductosDTO(productos);
        return ResponseEntity.ok(new ApiResponse("Productos encontrados", productoDTOS));
    }

    @GetMapping("/producto/{id}/producto")
    public ResponseEntity<ApiResponse> getProductoById(@PathVariable Integer id) {
        try {
            Producto producto = productoService.getProductoById(id);
            ProductoDTO productoDTO = productoService.convertirProductoToProductoDTO(producto);
            return ResponseEntity.ok(new ApiResponse("Producto encontrado", productoDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse> addProducto(@RequestBody ProductoDTO producto) {
        try {
            Producto productoNuevo = productoService.addProducto(producto);
            ProductoDTO productoDTO = productoService.convertirProductoToProductoDTO(productoNuevo);
            return ResponseEntity.ok(new ApiResponse("Producto agregado", productoDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/producto/{id}/update")
    public ResponseEntity<ApiResponse> updateProducto(@PathVariable Integer id, @RequestBody ProductoDTO producto) {
        try {
            Producto productoCargar = productoService.updateProducto(producto, id);
            ProductoDTO productoDTO = productoService.convertirProductoToProductoDTO(productoCargar);
            return ResponseEntity.ok(new ApiResponse("Producto actualizado", productoDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/producto/id/delete")
    public ResponseEntity<ApiResponse> deleteProducto(@PathVariable Integer id) {
        try {
            productoService.deleteProductoById(id);
            return ResponseEntity.ok(new ApiResponse("Producto eliminado", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/productos/por/marca-y-nombre")
    public ResponseEntity<ApiResponse> getProductoByMarcaAndNombre(@RequestParam String marca, @RequestParam String nombre) {
        try {
            List<Producto> productos = productoService.getProductoByNombreAndMarca(marca, nombre);
            if (productos.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Productos no encontrados", null));
            }
            List<ProductoDTO> productoDTOS = productoService.getProductosDTO(productos);
            return ResponseEntity.ok(new ApiResponse("Productos encontrados", productoDTOS));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/productos/por/marca-y-categoria")
    public ResponseEntity<ApiResponse> getProductoByMarcaAndCategoria(@RequestParam String marca, @RequestParam String categoria) {
        try {
            List<Producto> productos = productoService.getProductoByCategoriaAndMarca(marca, categoria);
            if (productos.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Productos no encontrados", null));
            }
            List<ProductoDTO> productoDTOS = productoService.getProductosDTO(productos);
            return ResponseEntity.ok(new ApiResponse("Productos encontrados", productoDTOS));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/productos/{nombre}/productos")
    public ResponseEntity<ApiResponse> getProductoByNombre(@PathVariable String nombre) {
        try {
            List<Producto> productos = productoService.getProductoByNombre(nombre);
            if (productos.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Productos no encontrados", null));
            }
            List<ProductoDTO> productoDTOS = productoService.getProductosDTO(productos);
            return ResponseEntity.ok(new ApiResponse("Productos encontrados", productoDTOS));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/productos/por-marca")
    public ResponseEntity<ApiResponse> getProductoByMarca(@RequestParam String marca) {
        try {
            List<Producto> productos = productoService.getProductoByMarca(marca);
            if (productos.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Productos no encontrados", null));
            }
            List<ProductoDTO> productoDTOS = productoService.getProductosDTO(productos);
            return ResponseEntity.ok(new ApiResponse("Productos encontrados", productoDTOS));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/productos/{categoria}/all/productos")
    public ResponseEntity<ApiResponse> getProductoByCategoria(@PathVariable String categoria) {
        try {
            List<Producto> productos = productoService.getProductoByCategoria(categoria);
            if (productos.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Productos no encontrados", null));
            }
            List<ProductoDTO> productoDTOS = productoService.getProductosDTO(productos);
            return ResponseEntity.ok(new ApiResponse("Productos encontrados", productoDTOS));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/productos/contar/por-marca-y-nombre")
     public ResponseEntity<ApiResponse> contarProductosPorMarcaAndNombre(@RequestParam String marca, @RequestParam String nombre){
       try {
           var productosContador = productoService.contarProductosPorMarcaAndNombre(marca, nombre);
              return ResponseEntity.ok(new ApiResponse("Productos contados", productosContador));
           }catch (Exception e){
              return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
       }
     }


}
