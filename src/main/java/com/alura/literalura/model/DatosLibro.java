package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(@JsonAlias("id") Long id, @JsonAlias("title") String titulo, @JsonAlias("download_count") Long descargas, @JsonAlias("authors") List<DatosAutor> autor, @JsonAlias("languages") List<String> idioma) {
}
