package com.JWTCurso.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal precioTotal = BigDecimal.ZERO;


    @OneToMany(mappedBy = "carrito")
    private Set<ItemCarrito> itemsCarrito;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public void addItemCarrito(ItemCarrito itemCarrito) {
        itemsCarrito.add(itemCarrito);
        itemCarrito.setCarrito(this);
        updatePrecioTotal();
    }

    public void removeItemCarrito(ItemCarrito itemCarrito) {
        this.itemsCarrito.remove(itemCarrito);
        itemCarrito.setCarrito(null);
        updatePrecioTotal();
    }


    public void updatePrecioTotal() {
        this.precioTotal = itemsCarrito.stream().map(item -> {
            BigDecimal precioUnidad = item.getPrecioUnidad();
                    if ( precioUnidad == null){
                        return BigDecimal.ZERO;
                    }
                    return precioUnidad.multiply(BigDecimal.valueOf(item.getCantidad()));
                }).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

}
