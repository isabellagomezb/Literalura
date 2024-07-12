package com.alura.Literalura.principal;

import com.alura.Literalura.model.Autor;
import com.alura.Literalura.model.Datos;
import com.alura.Literalura.model.DatosLibros;
import com.alura.Literalura.model.Libro;
import com.alura.Literalura.repository.IAutorRepository;
import com.alura.Literalura.repository.ILibroRepository;
import com.alura.Literalura.service.ConsumoAPI;
import com.alura.Literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private IAutorRepository autorRepository;
    private ILibroRepository libroRepository;
    private List<Libro> libros;
    private List<Autor> autor;

    public Principal(ILibroRepository libroRepository, IAutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void menu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """          
                    1 - Buscar Libros por Titulo
                    2 - Libros Registrados
                    3 - Autores Registrados
                    4 - Autores vivos en determinado año
                    5 - Libros por Idiomas
                    0 - Salir 
                     """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    librosRegistrados();
                    break;
                case 3:
                    autoresRegistrados();
                    break;
                case 4:
                    autorVivoEnAno();
                    break;
                case 5:
                    librosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void librosPorIdioma() {
        System.out.println("""
                Ingrese el idioma que desea
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                """);
        String idiomas = teclado.nextLine();
        libros = libroRepository.findByIdiomas(idiomas);
        for (Libro libros : libros);
        System.out.println(libros.toString());
    }

    private void autorVivoEnAno() {
        System.out.println("Ingrese un año: ");
        var ano = teclado.nextInt();
        autor = autorRepository.autoresVivosEnRespectivoAno(ano);
        if (autor.isEmpty()){
            System.out.println("No se encontró autores de ese año");
        }else{
            autor.forEach(System.out::println);
        }
    }

    private void autoresRegistrados() {
        autor = autorRepository.findAll();
        autor.forEach(System.out::println);
    }

    private void librosRegistrados(
    ) {
        libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void buscarLibro() {
        System.out.println("Ingrese el nombre del libro: ");
        var busquedaLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + busquedaLibro.replace(" ", "+"));

        List<DatosLibros> datosLibros = convierteDatos.obtenerDatos(json, Datos.class).resultados();

        Optional<DatosLibros> libroBuscado = datosLibros.stream()
                .filter(l -> l.titulo().toUpperCase().contains(busquedaLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            var libroEncontrado = libroBuscado.get();

            Libro libroExiste = libroRepository.findByTituloIgnoreCase(libroEncontrado.titulo());

            if (libroExiste != null) {
                System.out.println("El libro " + libroExiste.getTitulo() + "ya se registró");
            } else {
                var libro = new Libro(libroEncontrado);
                libroRepository.save(libro);
                System.out.println(libro);
            }


        } else {
            System.out.println("Libro no encontrado :(");
        }

    }
}

