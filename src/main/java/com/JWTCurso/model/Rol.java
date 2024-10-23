package com.JWTCurso.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;

    public Rol(String nombre) {
        this.nombre = nombre;
    }
    @ManyToMany(mappedBy = "roles")
    private Collection<Usuario> usuarios = new HashSet<>();
}
