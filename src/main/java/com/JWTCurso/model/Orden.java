package com.JWTCurso.model;

import com.JWTCurso.enums.OrdenEstado;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate fecha;
    private BigDecimal precioTotal;
    @Enumerated(EnumType.STRING)
    private OrdenEstado ordenEstado;

    @OneToMany(mappedBy = "orden")
    private Set<ItemOrden> itemOrdenes = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
