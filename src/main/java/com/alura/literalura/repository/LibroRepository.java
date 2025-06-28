package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {


    // 3- autores registrados
    @Query(value = "SELECT * FROM autores",nativeQuery = true)
    List<Autor> ontenerAutoresRegistrados();

    @Query("SELECT a FROM Autor a WHERE a.nacio <= :anio AND (a.murio >= :anio OR a.murio IS NULL)")
    List<Autor> obtenerAutoresVivos(Long anio);

@Query(value = """
    SELECT 
        l.id AS libro_id,
        l.titulo,
        l.descargas,
        l.idioma,
        STRING_AGG(a.nombre, ' - ') AS autores
    FROM libros l
    JOIN autores a ON a.libro_id = l.id
    WHERE l.idioma ILIKE :idioma
    GROUP BY l.id, l.titulo, l.descargas, l.idioma
    """, nativeQuery = true)
    List<Object[]> obtenerLibrosPorIdioma(@Param("idioma") String idioma);

}
