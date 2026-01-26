package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "perfiles")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bio;
    private String telefono;

    /**
     * BIDIRECCIONALIDAD (Punto 3)
     * mappedBy = "perfil" indica que la gestión de la FK está en el atributo "perfil"
     * de la clase Usuario. Esta clase NO crea columnas en BBDD para la relación.
     */
    @OneToOne(mappedBy = "perfil")
    private Usuario usuario;

    public Perfil() {}

    public Perfil(String bio, String telefono) {
        this.bio = bio;
        this.telefono = telefono;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    @Override
    public String toString() {
        return "Perfil{id=" + id + ", bio='" + bio + "', tel='" + telefono + "'}";
    }
}