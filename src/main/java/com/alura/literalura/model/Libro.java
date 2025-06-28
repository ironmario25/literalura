package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @Column(unique = true)
    private Long id;
    private String titulo;
    private Long descargas;
    @OneToMany(mappedBy = "libro",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Autor> autor;
    private String idioma;

    public  Libro(){}
    public Libro(Datos datosLibro){
        this.id = datosLibro.resultados().getFirst().id();
        this.titulo = datosLibro.resultados().getFirst().titulo();
        this.descargas = datosLibro.resultados().getFirst().descargas();

        // Crear un StringBuilder para concatenar los idiomas
        StringBuilder idiomasConcatenados = new StringBuilder();

        // Recorremos la lista de idiomas y concatenamos con comas
        List<String> idiomas = datosLibro.resultados().getFirst().idioma();
        for (int i = 0; i < idiomas.size(); i++) {
            idiomasConcatenados.append(idiomas.get(i));
            if (i < idiomas.size() - 1) {
                idiomasConcatenados.append(","); // Solo agregamos coma si no es el último
            }
        }
        if(datosLibro.resultados().getFirst().idioma().size()==0){
            this.idioma = null;
        }else {
            // Asignamos los idiomas concatenados
            this.idioma = idiomasConcatenados.toString();
        }

        setAutor(datosLibro.resultados().getFirst().autor().stream().map(d->new Autor(d)).collect(Collectors.toList()));
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autor) {
        autor.forEach(a->a.setLibro(this));
        this.autor = autor;
    }

    @Override
    public String toString() {
        return  "--------------------------------------"+'\n'+
                "id=" + id + '\n' +
                "titulo='" + titulo + '\n' +
                "descargas="+descargas+ '\n' +
                "autor="+getAutor()+'\n'+
               "--------------------------------------"+'\n';
    }
}
