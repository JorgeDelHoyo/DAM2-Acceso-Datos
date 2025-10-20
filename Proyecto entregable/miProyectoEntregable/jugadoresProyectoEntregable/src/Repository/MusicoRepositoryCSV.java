package Repository;

import Model.Musico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MusicoRepositoryCSV {

    private String nombreArchivo;

    public MusicoRepositoryCSV(String nombreArchivo){
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * Metodo para guardar los musicos en el archivo CSV
     * @param m
     * @throws IOException
     */
    public void guardar(Musico m) throws IOException{
        PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo, true));
        pw.println(m.getIdMusico()+","+m.getNombre()+","+m.getInstrumento());
        pw.close();
    }

    /**
     * Metodo para listar los musicos del archivo CSV
     * @return
     * @throws FileNotFoundException
     */
    public List<Musico> listarTodosMusicos() throws FileNotFoundException {
        List<Musico> musicos = new ArrayList<>();
        Scanner sc = new Scanner(new FileReader(nombreArchivo));

        while(sc.hasNextLine()){
            String[] datos = sc.nextLine().split(",");
            musicos.add(new Musico(Integer.parseInt(datos[0]), datos[1],datos[2],Integer.parseInt(datos[3])));
        }
        sc.close();
        return musicos;
    }
}
