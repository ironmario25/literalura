package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Long nacio;
    private Long murio;
    @ManyToOne
    private Libro libro;

    public Autor(){}
    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.nacio = datosAutor.nacio();
        this.murio = datosAutor.murio();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getNacio() {
        return nacio;
    }

    public void setNacio(Long nacio) {
        this.nacio = nacio;
    }

    public Long getMurio() {
        return murio;
    }

    public void setMurio(Long murio) {
        this.murio = murio;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return
                "nombre='" + nombre + '\'' +
                ", nacio=" + nacio +
                ", murio=" + murio;
    }
}
