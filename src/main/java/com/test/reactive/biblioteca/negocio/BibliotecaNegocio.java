package com.test.reactive.biblioteca.negocio;

import com.test.reactive.biblioteca.entidades.Libro;
import com.test.reactive.biblioteca.repositorio.IBibliotecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class BibliotecaNegocio implements IBibliotecaNegocio{

    @Autowired
    IBibliotecaRepository bibliotecaRepository;
    @Override
    public Flux<Libro> listarLibros() {
        return bibliotecaRepository.findAll().delayElements(Duration.ofSeconds(1)).log();
    }

    @Override
    public Flux<Libro> listarLibrosBackPressure(int limitRequest) {
        Flux<Libro> libroFlux = bibliotecaRepository.findAll().delayElements(Duration.ofSeconds(1)).log();
        return libroFlux.limitRate(limitRequest);
    }

    @Override
    public Mono<Libro> obtenerPorId(Long id) {
        return bibliotecaRepository.findById(id);
    }

    @Override
    public Mono<Libro> guardarLibro(Libro libro) {
        return bibliotecaRepository.save(libro).log();
    }

    @Override
    public Mono<ResponseEntity<Libro>> actualizarLibro(Long id, Libro libro) {
        return bibliotecaRepository.findById(id).
                flatMap(libroAnterior ->{
                    libroAnterior.setTitulo(libro.getTitulo());
                    libroAnterior.setAutor(libro.getAutor());
                    return bibliotecaRepository.save(libroAnterior);
                }).
                map( libroActualizado -> new ResponseEntity<>(libroActualizado, HttpStatus.OK) ).
                defaultIfEmpty(new ResponseEntity<>(HttpStatus.OK));
    }

    @Override
    public Mono<Libro> borrarLibro(Long id) {
        return bibliotecaRepository.findById(id).
                flatMap( libroEliminado ->
                        bibliotecaRepository.delete(libroEliminado).then(Mono.just(libroEliminado)));
    }
}
