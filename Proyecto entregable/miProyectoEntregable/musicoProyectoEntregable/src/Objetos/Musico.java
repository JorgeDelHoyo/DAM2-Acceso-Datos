package Objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Musico implements Serializable {
    private int idMusico; // ID del músico
    private String nombre; // Nombre del músico
    private String instrumento; // Instrumento del músico
    private List<Banda> bandas = new ArrayList<>();

    /**
     * Constructor Vacio
     */
    public Musico(){}

    /**
     * Constructor con parámetros
     * @param idMusico
     * @param nombre
     * @param instrumento
     */
    public Musico(int idMusico,String nombre, String instrumento){
        this.idMusico = idMusico;
        this.nombre = nombre;
        this.instrumento = instrumento;
    }

    // GETTER Y SETTERS //
    public int getIdMusico(){return idMusico;}

    public void setIdMusico(int idMusico){this.idMusico = idMusico;}

    public String getNombre(){return nombre;}

    public void setNombre(String nombre){this.nombre = nombre;}

    public String getInstrumento(){return instrumento;}

    public void setInstrumento(String instrumento){this.instrumento = instrumento;}

    public List<Banda> getBandas(){return bandas;}

    public void setBandas(List<Banda> bandas){this.bandas = bandas;}

    // GETTER Y SETTERS //

    /**
     * Metodo para mostrar los datos de una banda
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Musico{" +
                "ID: '" + idMusico + '\'' +
                ", Nombre: '" + nombre + '\'' +
                ", Instrumento: '" + instrumento + '\'' +
                ", Bandas[");
        for (Banda b : bandas) {
            sb.append("\n    ").append(b);
        }
        sb.append("\n]}");
        return sb.toString();
    }
}
