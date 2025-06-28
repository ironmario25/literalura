package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.AutoresConLibros;
import com.alura.literalura.model.Datos;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com//books/";
    private final String BUSCAR = "?search=";
    private LibroRepository libroRepository;

    public Principal(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public void muestrarMenu() {

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año   
                    5 - Listar libros por idioma   
                    
                    0 - Salir
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                        buscarLibroPorTitulo();
                    break;
                case 2:
                        listarLibrosRegistrados();
                    break;
                case 3:
                        listarAutoresRegistrados();
                    break;
                case 4:
                        obtenerAutoresVivos();
                    break;
                case 5:
                        listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }



    private void buscarLibroPorTitulo() {
        System.out.println("escriba el titulo del libro a buscar: ");
        var nombreLibro = teclado.nextLine();
        var consumoApi = new ConsumoApi();
		var json = consumoApi.obtenerDatos(URL_BASE+BUSCAR+nombreLibro.replace(" ","+"));
//		System.out.println(json);
        ConvierteDatos coversor = new ConvierteDatos();
		var datos = coversor.obtenerDatos(json, Datos.class);
        if(datos.resultados().isEmpty()){
            System.out.println("--------------------------------------");
            System.out.println("no se encontraron resultados");
            System.out.println("--------------------------------------");
        }else {
            System.out.println("--------------------------------------");
            System.out.println("titulo: "+datos.resultados().getFirst().titulo());
            System.out.println("descargas: "+datos.resultados().getFirst().descargas());
            System.out.println("autor: ");
            datos.resultados().getFirst().autor().forEach(a->System.out.println(a.nombre()));
            System.out.println("idioma: ");
            datos.resultados().getFirst().idioma().forEach(System.out::println);
            System.out.println("--------------------------------------");

            Libro unLibro = new Libro(datos);

            boolean yaExiste = libroRepository.existsById(unLibro.getId());

            if (yaExiste) {
                System.out.println("--------------------------------------");
                System.out.println("libro ya registrado");
                System.out.println("--------------------------------------");

                return;
            }
                libroRepository.save(unLibro);




        }


    }

    private void listarLibrosRegistrados() {
        List<Libro> listaLibros = libroRepository.findAll();
        listaLibros.forEach(System.out::println);
    }
    private void listarAutoresRegistrados(){
        List<Autor> autores = libroRepository.ontenerAutoresRegistrados();

        Map<String, AutoresConLibros> autoresMap = autores.stream()
                .collect(Collectors.toMap(
                        Autor::getNombre,
                        autor -> {
                            AutoresConLibros acl = new AutoresConLibros(autor.getNombre(),autor.getNacio(),autor.getMurio());
                            acl.agregarLibro(autor.getLibro().getTitulo());
                            return acl;
                        },
                        (existente, nuevo) -> {
                            existente.agregarLibro(nuevo.getLibros().get(0)); // fusionar libro nuevo
                            return existente;
                        }
                ));

        List<AutoresConLibros> listaFinal = new ArrayList<>(autoresMap.values());
        listaFinal.forEach(System.out::println);
    }


    private void obtenerAutoresVivos(){
        System.out.println("Ingrese el año: ");
        Long opcion = teclado.nextLong();
        teclado.nextLine();  // Para limpiar el buffer

        // Obtener los resultados de los autores vivos en el año dado con sus libros
        List<Autor> autores = libroRepository.obtenerAutoresVivos(opcion);
        System.out.println(autores);
        Map<String, AutoresConLibros> autoresMap = autores.stream()
                .collect(Collectors.toMap(
                        Autor::getNombre,
                        autor -> {
                            AutoresConLibros acl = new AutoresConLibros(autor.getNombre(),autor.getNacio(),autor.getMurio());
                            acl.agregarLibro(autor.getLibro().getTitulo());
                            return acl;
                        },
                        (existente, nuevo) -> {
                            existente.agregarLibro(nuevo.getLibros().get(0)); // fusionar libro nuevo
                            return existente;
                        }
                ));

        List<AutoresConLibros> listaFinal = new ArrayList<>(autoresMap.values());
        listaFinal.forEach(System.out::println);

    }

    private void listarLibrosPorIdioma(){
        System.out.println("Ingrese el idioma: ");
        var menuIdioma = """
                    es - español
                    en - ingles
                    fr - frances
                    pt - portugues
                    
                    0 - volver atras
                    """;
        System.out.println(menuIdioma);
        String opcion = teclado.nextLine();
        if(opcion.equals("0")){
            System.out.println("salir");
        }else {
            List<Object[]> resultados = libroRepository.obtenerLibrosPorIdioma("%"+opcion+"%");
            if(resultados.isEmpty()){
                System.out.println("--------------------------------------");
                System.out.println("no hay resultados");
                System.out.println("--------------------------------------");
            }
            for (Object[] resultado : resultados) {
                Long id = (Long) resultado[0];
                String titulo = (String) resultado[1];
                Long descargas= (Long) resultado[2];
                String idioma = (String) resultado[3];
                String autor = (String) resultado[4];
                System.out.println("--------------------------------------");
                System.out.println("id: " + id);
                System.out.println("titulo: " + titulo);
                System.out.println("descargas: " + descargas);
                System.out.println("idioma: " + idioma);
                System.out.println("autor: " + autor);
                System.out.println("--------------------------------------");
            }
        }

    }
}
