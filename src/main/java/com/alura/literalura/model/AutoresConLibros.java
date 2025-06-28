package com.alura.literalura.model;

import java.util.ArrayList;
import java.util.List;

public class AutoresConLibros {
    private String nombre;
    private Long nacio;
    private Long murio;
    private List<String> libros;

    public AutoresConLibros(String nombre, Long nacio, Long murio) {
        this.nombre = nombre;
        this.nacio = nacio;
        this.murio = murio;
        this.libros = new ArrayList<>();
    }

    public void agregarLibro(String libro) {
        if (!libros.contains(libro)) {
            libros.add(libro);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Long getNacio() {
        return nacio;
    }

    public Long getMurio() {
        return murio;
    }

    public List<String> getLibros() {
        return libros;
    }

    @Override
    public String toString() {
        return "Autor: " + nombre +
                " | Nació: " + nacio +
                " | Murió: " + murio +
                " | Libros: " + String.join(", ", libros);
    }
}