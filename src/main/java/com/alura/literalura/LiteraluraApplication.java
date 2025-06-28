package com.alura.literalura;

import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.alura.literalura.principal.Principal;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
//inyeccion de dependencia
	@Autowired
	LibroRepository libroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository);
		principal.muestrarMenu();
//		var consumoApi = new ConsumoApi();
//		var json = consumoApi.obtenerDatos("https://gutendex.com//books/?search=Moby%20Dick");
//		System.out.println(json);
//		ConvierteDatos coversor = new ConvierteDatos();
//		var datos = coversor.obtenerDatos(json, DatosLibro.class);
//		System.out.println(datos);
	}
}
