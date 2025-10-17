package GestorEquipoJugador;

import javax.management.modelmbean.ModelMBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorEquipoJugador {

    private List listaEquipos = new ArrayList();
    private List listaJugadores = new ArrayList();

    private Map<Model.Equipo, Model.Jugador> equiposJugador = new HashMap<>();


    public List getListaEquipos() {
        return listaEquipos;
    }

    public void setListaEquipos(List listaEquipos) {
        this.listaEquipos = listaEquipos;
    }

    public List getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(List listaJugadores) {
        this.listaJugadores = listaJugadores;
    }
}