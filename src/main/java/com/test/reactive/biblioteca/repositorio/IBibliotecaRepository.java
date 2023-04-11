package com.test.reactive.biblioteca.repositorio;

import com.test.reactive.biblioteca.entidades.Libro;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface IBibliotecaRepository extends ReactiveCrudRepository<Libro,Long> {
}
