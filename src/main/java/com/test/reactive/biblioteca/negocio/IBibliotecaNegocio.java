package com.test.reactive.biblioteca.negocio;

import com.test.reactive.biblioteca.entidades.Libro;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBibliotecaNegocio {
    public Flux<Libro> listarLibros();
    public Flux<Libro> listarLibrosBackPressure(int limitRequest);
    public Mono<Libro> obtenerPorId(Long id);
    public Mono<Libro> guardarLibro(Libro libro);
    public Mono<ResponseEntity<Libro>> actualizarLibro(Long id, Libro libro);
    public Mono<Libro> borrarLibro(Long id);
}
