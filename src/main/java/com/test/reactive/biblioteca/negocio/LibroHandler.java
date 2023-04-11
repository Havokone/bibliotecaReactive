package com.test.reactive.biblioteca.negocio;

import com.test.reactive.biblioteca.entidades.Libro;
import com.test.reactive.biblioteca.repositorio.IBibliotecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class LibroHandler {
    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    @Autowired
    IBibliotecaRepository bibliotecaRepository;
    public Mono<ServerResponse> listarLibros(ServerRequest serverRequest){
        return ServerResponse.
                        ok().
                        contentType(MediaType.APPLICATION_JSON).
                        body(bibliotecaRepository.findAll(), Libro.class);
    }
    public Mono<ServerResponse> obtenerLibroPorId(ServerRequest serverRequest){
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        Mono<Libro> itemMono = bibliotecaRepository.findById(id);
        return itemMono.flatMap(item ->
                                ServerResponse.ok().
                                        contentType(MediaType.APPLICATION_JSON).
                                        body(fromValue(item)).
                                        switchIfEmpty(notFound) );
    }
    public Mono<ServerResponse> guardarLibro(ServerRequest serverRequest){
        Mono<Libro> libroMono = serverRequest.bodyToMono(Libro.class);
        return libroMono.flatMap(libro ->
                ServerResponse.status(HttpStatus.CREATED).
                        contentType(MediaType.APPLICATION_JSON).
                        body(bibliotecaRepository.save(libro), Libro.class) );
    }

    public Mono<ServerResponse> borrarLibro(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        Mono<Void> libroBorrado = bibliotecaRepository.deleteById(Long.valueOf(id));
        return ServerResponse.ok().
                contentType(MediaType.APPLICATION_JSON).
                body(libroBorrado,Void.class);
    }

    public Mono<ServerResponse> actualizarLibro(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        Mono<Libro> libroActualizado = serverRequest.bodyToMono(Libro.class).
                                                    log("mono: ").
                                                    flatMap(libro ->bibliotecaRepository.findById(Long.valueOf(id)).
                                                                                        log().
                                                                                        flatMap(libroAnterior ->{
                                                                                            libroAnterior.setAutor(libro.getAutor());
                                                                                            libroAnterior.setTitulo(libro.getTitulo());
                                                                                            return bibliotecaRepository.save(libroAnterior).log();
                                                                                        }));
        return libroActualizado.flatMap(libro ->
                ServerResponse.ok().
                        contentType(MediaType.APPLICATION_JSON).
                        body(fromValue(libro)).
                        switchIfEmpty(notFound) );
    }

}
