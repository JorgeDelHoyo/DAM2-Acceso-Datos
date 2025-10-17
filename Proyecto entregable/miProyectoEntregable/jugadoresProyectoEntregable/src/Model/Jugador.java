package Model;

import java.io.Serializable;

public class Jugador implements Serializable {
    private int idJugador;
    private String nombreJugador;
    private int idEquipo;

    public Jugador(){

    }

    public Jugador(int idJugador, String nombreJugador, int idEquipo){
        this.idJugador = idJugador;
        this.nombreJugador = nombreJugador;
        this.idEquipo = idEquipo;
    }

    public int getIdJugador(){return idJugador;}

    public void setIdJugador(int idJugador){this.idJugador = idJugador;}

    public String nombreJugador(){return nombreJugador;}

    public void setNombreJugador(String nombreJugador){this.nombreJugador = nombreJugador;}

    public int getIdEquipo(){return idEquipo;}

    public void setIdEquipo(int idEquipo){this.idEquipo = idEquipo;}

    @Override
    public String toString() {
        return "Jugador{" +
                "idJugador=" + idJugador +
                ", nombreJugador='" + nombreJugador + '\'' +
                ", idEquipo=" + idEquipo +
                '}';
    }
}
