package com.alura.Literalura.repository;

import com.alura.Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombre(String nombre);


    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :ano AND a.fechaFallecimiento >= :ano")
    List<Autor> autoresVivosEnRespectivoAno(int ano);


}