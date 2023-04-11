package com.test.reactive.biblioteca.rest;

import com.test.reactive.biblioteca.negocio.LibroHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class LibroRouter {
    @Bean
    public RouterFunction<ServerResponse> libroRouterFunc(LibroHandler libroHandler){
        return RouterFunctions.route(
          GET("/api/func/libros/").and(accept(MediaType.APPLICATION_JSON)),libroHandler::listarLibros).
                andRoute(GET("/api/func/libros/"+"{id}").and(accept(MediaType.APPLICATION_JSON)),libroHandler::obtenerLibroPorId ).
                andRoute(POST("/api/func/libros/").and(accept(MediaType.APPLICATION_JSON)),libroHandler::guardarLibro ).
                andRoute(PUT("/api/func/libros/"+"{id}").and(accept(MediaType.APPLICATION_JSON)),libroHandler::actualizarLibro ).
                andRoute(DELETE("/api/func/libros/"+"{id}").and(accept(MediaType.APPLICATION_JSON)),libroHandler::borrarLibro )
        ;
    }
}
