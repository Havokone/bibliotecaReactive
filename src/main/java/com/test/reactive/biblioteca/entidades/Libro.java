package com.test.reactive.biblioteca.entidades;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
@Getter
@Setter
public class Libro {
    @Id
    private Long id;
    private String titulo;
    private String autor;
    public Libro(){}
    public Libro(Long id, String titulo, String autor){
        this.setId(id);
        this.setAutor(autor);
        this.setTitulo(titulo);
    }
}
