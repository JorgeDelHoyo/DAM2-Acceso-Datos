import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroBinario {

    /**
     * Metodo para leer un fichero binario
     * @param ruta
     * @return
     * @throws IOException
     */
    public List<Producto> leerBinario(String ruta) throws IOException{
        ObjectInputStream ois = null;
        List<Producto> listaProductos = new ArrayList<>();
        try{
            ois = new ObjectInputStream(new FileInputStream(ruta));
            while(true){
                Producto p = (Producto) ois.readObject();
                listaProductos.add(p);
            }
        }catch (IOException  | ClassNotFoundException cnf){
            cnf.printStackTrace();
        }finally {
            if(ois != null){
                ois.close();
            }
        }
        return listaProductos;
    }

    /**
     * Metodo para escribir un fichero binario
     * @param lista
     * @param ruta
     * @throws IOException
     */
    public void escribirBinario(List<Producto> lista, String ruta)throws IOException{
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(new FileOutputStream(ruta));
            for(Producto p : lista){
                oos.writeObject(p);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(oos != null){
                oos.close();
            }
        }
    }

    public List<Alumno> leerBinarioAlumno(String ruta) throws IOException {
        List<Alumno> lista = new ArrayList<>();
        ObjectInputStream ois = null;
        try{
            ois = new ObjectInputStream(new FileInputStream(ruta));
            while(true){
                Alumno a = (Alumno) ois.readObject();
                lista.add(a);
            }
        }catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }finally {
            if (ois != null) {
                ois.close();
            }
        }
        return lista;
    }

    public void escribirBinarioAlumno(List<Alumno>lista, String ruta) throws IOException{
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(new FileOutputStream(ruta));
            for(Alumno a : lista){
                oos.writeObject(a);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            if(oos != null) {
                oos.close();
            }
        }
    }


}
