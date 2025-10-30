import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExamenPruebaFicheroCSV {

    public List<ExamenPrueba> leerCSV(String ruta)throws IOException {
        List<ExamenPrueba> lista = new ArrayList<>();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(ruta));
            String linea;
            while((linea = br.readLine()) != null){
                String[] partesLista = linea.split(",");
                int id = Integer.parseInt(partesLista[0]);
                String nombre = partesLista[1];
                float notaMaxima = Float.parseFloat(partesLista[2]);
                String fecha =  partesLista[3];
                boolean aprobado = Boolean.parseBoolean(partesLista[4]);

                lista.add(new ExamenPrueba(id,nombre,notaMaxima,fecha,aprobado));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            if(br != null){
                br.close();
            }
        }
        return lista;
    }

    public void escribirCSV (List<ExamenPrueba> lista, String ruta)throws IOException {
        BufferedWriter bw = null;
        try{
            bw = new BufferedWriter(new FileWriter(ruta));
            for(ExamenPrueba e: lista){
                bw.write(e.getId()+","+e.getNombre()+","+e.getNotaMaxima()+","+e.getFecha()+","+e.isAprobado());
                bw.newLine();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            if(bw != null){
                bw.close();
            }
        }
    }
}
