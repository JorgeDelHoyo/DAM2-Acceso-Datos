import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroBinario {
    public void escribirBinario(List<Producto> lista, String ruta) throws Exception{
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))){
            for(Producto p : lista){
                oos.writeObject(p);
            }
        }
    }

    public List<Producto> leerBinario(String ruta)throws Exception{
        List<Producto> lista = new ArrayList<>();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))){
            while(true){
                try{
                    Producto p = (Producto) ois.readObject();
                    lista.add(p);
                }catch (EOFException e){
                    break;
                }
            }
        }
        return lista;
    }
}
