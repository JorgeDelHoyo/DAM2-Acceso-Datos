package org.example.model;

import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;

public class Post {
    private ObjectId id; // Mongo mapea esto automáticamente al campo "_id"
    private String titulo;
    private String mensaje;
    private List<String> etiquetas; // Array
    private Date fechaPublicacion;
    private List<Comentario> comentarios; // Documentos embebidos

    // Constructor vacío (Obligatorio)
    public Post() {}

    public Post(String titulo, String mensaje, List<String> etiquetas, Date fechaPublicacion, List<Comentario> comentarios) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.etiquetas = etiquetas;
        this.fechaPublicacion = fechaPublicacion;
        this.comentarios = comentarios;
    }

    // Getters y Setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public List<String> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<String> etiquetas) { this.etiquetas = etiquetas; }

    public Date getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(Date fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    public List<Comentario> getComentarios() { return comentarios; }
    public void setComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }

    @Override
    public String toString() {
        return "\nPost{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", etiquetas=" + etiquetas +
                ", comentarios=" + comentarios +
                '}';
    }
}