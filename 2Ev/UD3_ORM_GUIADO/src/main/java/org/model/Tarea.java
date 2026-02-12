package org.model;

import jakarta.persistence.*;

@Entity
@Table(name="tareas")

public class Tarea {

    /**
     * Estrategia usada para generar un ID identificativo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String titulo;
    private boolean completada;

    public Tarea(){

    }

    public Tarea(String titulo){
        this.titulo = titulo;
        this.completada = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    @Override
    public String   toString() {
        return "Tarea{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", completada=" + completada +
                '}';
    }
}
