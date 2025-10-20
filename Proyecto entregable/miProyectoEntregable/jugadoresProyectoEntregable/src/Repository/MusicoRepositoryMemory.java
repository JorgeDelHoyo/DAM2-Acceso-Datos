package Repository;

import Model.Musico;

import java.util.ArrayList;
import java.util.List;

public class MusicoRepositoryMemory{
    private List<Musico> musicos;

    /**
     * Constructor que inicializa la lista
     */
    public MusicoRepositoryMemory(){
        this.musicos = new ArrayList<>();
    }

    /**
     * Metodo para guardar un musico en la lista
     * @param m
     */
    public void guardarMusico(Musico m){
            musicos.add(m);
    }

    /**
     * Metodo para borrar un músico de la lista
     * @param m
     */
    public void borrarMusico(Musico m){
            musicos.remove(m);
    }

    /**
     * Metodo para listar todos los músicos
     * @return
     */
    public List<Musico> listarMusicos(){
        return musicos;
    }

}
