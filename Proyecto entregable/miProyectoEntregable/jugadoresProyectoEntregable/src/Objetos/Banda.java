package Model;

import java.io.Serializable;

public class Banda implements Serializable {

    private int idBanda;
    private String nombreBanda;

    public Banda(){

    }

    public Banda(int idBanda, String nombreBanda){
        this.idBanda = idBanda;
        this.nombreBanda = nombreBanda;
    }

    public int getIdBanda(){return idBanda;}

    public void setIdBanda(int idBanda){this.idBanda = idBanda;}

    public String getNombreBanda(){return nombreBanda;}

    public void setNombreBanda(String nombreBanda){this.nombreBanda = nombreBanda;}
}
