package com.JWTCurso.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String archivoNombre;
    private String archivoTipo;
    @Lob
    private Blob imagen;
    private String descargaURL;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
