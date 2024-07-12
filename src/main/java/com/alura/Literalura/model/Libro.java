package com.alura.Literalura.model;


import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table (name = "Libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Autor autor;
    private String idiomas;
    private Double numeroDescargas;

    public Libro(){}

    public Libro(DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        Optional<DatosAutor> autor = datosLibros.autor().stream()
                .findFirst();

        if (autor.isPresent()){
            this.autor = new Autor(autor.get());
        }else {
            System.out.println("No se encontró al autor");
        }

        this.idiomas = datosLibros.idiomas().get(0);
        this. numeroDescargas = datosLibros.numeroDescargas();
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

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "Libro: " + titulo + "\n" +
                "Autor: " + autor + "\n" +
                "Idiomas: " + idiomas + "\n" +
                "Numero De Descargas: " + numeroDescargas + "\n";

    }
}
