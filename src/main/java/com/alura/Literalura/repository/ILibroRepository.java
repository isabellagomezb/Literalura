package com.alura.Literalura.repository;


import com.alura.Literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ILibroRepository extends JpaRepository<Libro, Long> {
    Libro findByTituloIgnoreCase(String titulo);
    List<Libro> findByIdiomas(String idiomas);

}
