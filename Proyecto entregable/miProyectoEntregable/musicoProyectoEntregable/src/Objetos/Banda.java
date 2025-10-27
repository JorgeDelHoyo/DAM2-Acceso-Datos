package Objetos;

import java.io.Serializable;

public class Banda implements Serializable {

    private int idBanda; // ID de la banda
    private String nombreBanda; // Nombre de la banda

    /**
     * Constructor vacio
     */
    public Banda(){}

    /**
     * Constructor con parámetros
     * @param idBanda
     * @param nombreBanda
     */
    public Banda(int idBanda, String nombreBanda){
        this.idBanda = idBanda;
        this.nombreBanda = nombreBanda;
    }

    // GETTER Y SETTERS //
    public int getIdBanda(){return idBanda;}

    public void setIdBanda(int idBanda){this.idBanda = idBanda;}

    public String getNombreBanda(){return nombreBanda;}

    public void setNombreBanda(String nombreBanda){this.nombreBanda = nombreBanda;}

    // GETTER Y SETTERS //

    /**
     * Metodo para mostrar los datos de una banda
     * @return
     */
    @Override
    public String toString() {
        return "Banda{" +
                "IDBanda: " + idBanda +
                ", NombreBanda: '" + nombreBanda + '\'' +
                '}';
    }
}
