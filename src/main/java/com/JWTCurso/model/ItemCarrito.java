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
public class ItemCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int cantidad;
    private BigDecimal precioUnidad;
    private BigDecimal precioTotal;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto Producto;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    public void setPrecioTotal() {this.precioTotal = this.precioUnidad.multiply(new BigDecimal(cantidad));}
}
