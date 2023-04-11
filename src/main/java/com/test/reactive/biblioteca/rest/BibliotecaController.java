package com.test.reactive.biblioteca.rest;

import com.test.reactive.biblioteca.entidades.Libro;
import com.test.reactive.biblioteca.negocio.IBibliotecaNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive/api/libros")
public class BibliotecaController {
    @Autowired
    private IBibliotecaNegocio bibliotecaNegocio;

    @GetMapping(value = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Libro> listarLibros(){
        return bibliotecaNegocio.listarLibros();
    }

    @GetMapping(value = "/presure/{nro}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Libro> listarLibrosBackPressure(@PathVariable int nro){
        return bibliotecaNegocio.listarLibrosBackPressure(nro);
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<Libro> obtenerPorId(@PathVariable Long id){
        return bibliotecaNegocio.obtenerPorId(id);
    }

    @PostMapping(value = "/")
    public Mono<Libro> guardarLibro(@RequestBody Libro libro){
        return bibliotecaNegocio.guardarLibro(libro);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Libro>> actualizarLibro(@PathVariable Long id, @RequestBody Libro libro){
        return bibliotecaNegocio.actualizarLibro(id,libro);
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> borrarLibro(@PathVariable Long id){
        return bibliotecaNegocio.borrarLibro(id).
                map( r -> ResponseEntity.ok().<Void>build()).
                defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
