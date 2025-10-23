package Objetos;

import java.io.Serializable;

public class Musico implements Serializable {
    private int idMusico; // ID del músico
    private String nombre; // Nombre del músico
    private String instrumento; // Instrumento del músico
    private int idBanda; // ID de la banda en la que toca

    /**
     * Constructor Vacio
     */
    public Musico(){

    }

    /**
     * Constructor con parámetros
     * @param idMusico
     * @param nombre
     * @param instrumento
     * @param idBanda
     */
    public Musico(int idMusico,String nombre, String instrumento, int idBanda){
        this.idMusico = idMusico;
        this.nombre = nombre;
        this.instrumento = instrumento;
        this.idBanda = idBanda;
    }

    public int getIdMusico(){return idMusico;}

    public void setIdMusico(int idMusico){this.idMusico = idMusico;}

    public String getNombre(){return nombre;}

    public void setNombre(String nombre){this.nombre = nombre;}

    public String getInstrumento(){return instrumento;}

    public void setInstrumento(String instrumento){this.instrumento = instrumento;}

    public int getIdBanda(){return idBanda;}

    public void setIdBanda(int idBanda){this.idBanda = idBanda;}

    @Override
    public String toString() {
        return "Musico{" +
                "ID: " + idMusico +
                ", Nombre: '" + nombre + '\'' +
                ", Instrumento: '" + instrumento + '\'' +
                ", ID Banda: " + idBanda +
                '}';
    }
}
