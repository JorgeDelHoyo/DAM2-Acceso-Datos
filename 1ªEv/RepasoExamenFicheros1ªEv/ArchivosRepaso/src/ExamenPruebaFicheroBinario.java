import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExamenPruebaFicheroBinario {

    public List<ExamenPrueba> leerBinario (String ruta) throws IOException {
        List<ExamenPrueba> lista = new ArrayList<>();
        ObjectInputStream ois = null;
        try{
            ois = new ObjectInputStream(new FileInputStream(ruta));
            while(true){
                try{
                    ExamenPrueba e = (ExamenPrueba)ois.readObject();
                    lista.add(e);
                }catch (EOFException e){
                    break;
                }catch (ClassNotFoundException e){
                    System.out.println(e.getMessage());
                }
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }finally{
            if(ois != null){
                ois.close();
            }
        }
        return lista;
    }


    public void escribirBinario(List<ExamenPrueba>lista, String ruta)throws IOException{
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(new FileOutputStream(ruta));
            for(ExamenPrueba e : lista){
                oos.writeObject(e);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            if(oos != null){
                oos.close();
            }
        }
    }
}
