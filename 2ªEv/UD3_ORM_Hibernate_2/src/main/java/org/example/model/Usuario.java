package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    /**
     * RELACIÓN ONE TO ONE
     * 1. Dueño de la relación: Quien tiene el @JoinColumn.
     * 2. CascadeType.ALL: Incluye PERSIST (guardar usuario guarda perfil) y REMOVE (borrar usuario borra perfil).
     * 3. FetchType: Lo configuramos en LAZY para probar el punto 5 del ejercicio.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id", unique = true) // FK en la tabla 'usuarios'
    private Perfil perfil;

    public Usuario() {}

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    // Método helper para la bidireccionalidad (Buena práctica punto 3)
    public void asignarPerfil(Perfil p) {
        this.perfil = p;
        p.setUsuario(this); // Mantenemos la coherencia en memoria
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + nombre + "'}";
    }
}