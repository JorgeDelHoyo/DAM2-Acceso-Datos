package org.example.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "editoriales")
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String pais;

    @OneToMany(mappedBy = "editorial", cascade = CascadeType.ALL)
    private List<Libro> libros = new ArrayList<>();

    public Editorial(){}

    public Editorial(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    public void addLibro(Libro libro){
        libros.add(libro);
        libro.setEditorial(null);
    }

    public void removeLibro(Libro libro){
        libros.remove(libro);
        libro.setEditorial(null);
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getPais() {return pais;}
    public void setPais(String pais) {this.pais = pais;}

    public List<Libro> getLibros() {return libros;}
    public void setLibros(List<Libro> libros) {this.libros = libros;}

    @Override
    public String toString() {
        return "Editorial{id=" + id + ", nombre='" + nombre + "'}";
    }
}
