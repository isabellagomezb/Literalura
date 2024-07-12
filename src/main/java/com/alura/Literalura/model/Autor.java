package com.alura.Literalura.model;


import jakarta.persistence.*;
import jdk.jfr.Enabled;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table( name = "Autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private String fechaNacimiento;
    private String fechaFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    private List<Libro> libros;

    public Autor(){
        this.libros=new ArrayList<>();
    }

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaFallecimiento = datosAutor.fechaFallecimiento();
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(String fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
        for (Libro libro : libros){
            libro.setAutor(this);
        }
    }

    @Override
    public String toString() {
        return "Autor: " + nombre + "\n" +
                "Fecha De Nacimiento: " + fechaNacimiento + "\n" +
                "Fecha De Fallecimiento: " + fechaFallecimiento + "\n" +
                "Libros: " + libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.toList())+ "\n";
    }
}
