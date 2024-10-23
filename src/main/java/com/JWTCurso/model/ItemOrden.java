package com.JWTCurso.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int cantidad;
    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public ItemOrden(int cantidad, BigDecimal precioUnidad, Orden orden, Producto producto) {
        this.cantidad = cantidad;
        this.precio = precioUnidad;
        this.orden = orden;
        this.producto = producto;
    }
}
