package com.JWTCurso.service.producto;

import com.JWTCurso.dto.ImagenDTO;
import com.JWTCurso.dto.ProductoDTO;
import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Categoria;
import com.JWTCurso.model.Imagen;
import com.JWTCurso.model.Producto;
import com.JWTCurso.repository.CategoriaRepository;
import com.JWTCurso.repository.ImagenRepository;
import com.JWTCurso.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImp implements ProductoService{
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;
    private final ImagenRepository imagenRepository;

    @Override
    public Producto getProductoById(Integer id) {
        return productoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Producto no encontrado"));
    }

    @Override
    public Producto addProducto(ProductoDTO productoDTO) {
        Categoria categoria = Optional.ofNullable(categoriaRepository.findByNombre(productoDTO.getCategoria().getNombre()))
                .orElseGet(() -> {
                    Categoria newCategoria = new Categoria(productoDTO.getCategoria().getNombre());
                    return categoriaRepository.save(newCategoria);
                });
        productoDTO.setCategoria(categoria);
        return productoRepository.save(crearProducto(productoDTO, categoria));
    }

    private Producto crearProducto(ProductoDTO productoDTO, Categoria categoria) {
        return new Producto(
                productoDTO.getNombre(),
                productoDTO.getMarca(),
                productoDTO.getPrecio(),
                productoDTO.getStock(),
                productoDTO.getDescripcion(),
                categoria
        );
    }


    @Override
    public void deleteProductoById(Integer id) {
        productoRepository.deleteById(id);

    }

    @Override
    public Producto updateProducto(ProductoDTO productoDTO, Integer id) {
        return productoRepository.findById(id)
                .map(productoExistente -> updatePrductoExistente(productoExistente, productoDTO))
                .map(productoRepository :: save)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado!"));

    }

    public Producto updatePrductoExistente(Producto productoExistente, ProductoDTO productoDTO) {
        productoExistente.setNombre(productoDTO.getNombre());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setMarca(productoDTO.getMarca());
        productoExistente.setPrecio(productoDTO.getPrecio());
        productoExistente.setStock(productoDTO.getStock());

        Categoria categoria = categoriaRepository.findByNombre(productoDTO.getCategoria().getNombre());
        productoExistente.setCategoria(categoria);
        return productoExistente;
    }

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> getProductoByCategoria(String categoria) {
        return productoRepository.findByCategoriaNombre(categoria);
    }

    @Override
    public List<Producto> getProductoByMarca(String marca) {
        return productoRepository.findByMarca(marca);
    }

    @Override
    public List<Producto> getProductoByCategoriaAndMarca(String categoria, String marca) {
        return productoRepository.findByCategoriaNombreAndMarca(categoria, marca);
    }

    @Override
    public List<Producto> getProductoByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    @Override
    public List<Producto> getProductoByNombreAndMarca(String nombre, String marca) {
        return productoRepository.findByNombreAndMarca(nombre, marca);
    }

    @Override
    public Long contarProductosPorMarcaAndNombre(String marca, String nombre) {
        return productoRepository.countProductoByMarcaAndNombre(marca, nombre);
    }

    @Override
    public List<ProductoDTO> getProductosDTO(List<Producto> productos) {
        return productos.stream().map(this::convertirProductoToProductoDTO).toList();
    }

    public ProductoDTO convertirProductoToProductoDTO(Producto producto) {
        ProductoDTO productoDTO= modelMapper.map(producto, ProductoDTO.class);
        List<Imagen> imagenes= imagenRepository.findByProductoId(producto.getId());
        List<ImagenDTO> imagenDTOs= imagenes.stream()
                .map(imagen -> modelMapper.map(imagenes, ImagenDTO.class))
                .toList();
        productoDTO.setImagenes(imagenDTOs);
        return productoDTO;
    }
}
