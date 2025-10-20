package Service;

import Model.Musico;
import Repository.MusicoRepository;
import Repository.MusicoRepositoryCSV;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MusicoService{

    MusicoRepository musicoRepo;

    public MusicoService(MusicoRepository musicoRepo){
        this.musicoRepo = musicoRepo;
    }

    /**
     * Metodo para guardar un musico si no existe ya ese ID
     * @param m
     * @throws IOException
     */
    public void guardar(Musico m) throws IOException {
        List<Musico> musicos = listarTodos();
        boolean existeIDMusico = false;
        for(Musico musicoTemp : musicos){
            if(musicoTemp.getIdMusico() == m.getIdMusico()){
                existeIDMusico = true;
            }
        }

        if(!existeIDMusico){
            musicoRepo.guardarMusico(m);
        }else{
            System.out.println("Ese ID ya existe");
        }
    }

    /**
     * Metodo para listar todos los musicos
     * @return
     * @throws IOException
     */
    public List<Musico> listarTodos() throws IOException {
        return musicoRepo.findAll();
    }
}
