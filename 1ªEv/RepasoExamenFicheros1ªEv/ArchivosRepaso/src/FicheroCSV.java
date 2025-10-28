import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroCSV {

    /**
     * Metodo para leer un archivo CSV (texto) separado por comas
     * @param ruta
     * @return
     * @throws IOException
     */
    public List<Producto> leerCSV (String ruta) throws IOException {
        BufferedReader br = null;
        List<Producto> lista = new ArrayList<>();
        try{
            br = new BufferedReader(new FileReader(ruta));
            String linea;
            while((linea = br.readLine()) != null ){
                String[] partesLista = linea.split(",");
                int id = Integer.parseInt(partesLista[0]);
                String nombre = partesLista[1];
                double precio = Double.parseDouble(partesLista[2]);

                Producto p = new Producto(id, nombre, precio);

                lista.add(p);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(br != null){
                br.close();
            }
        }
        return lista;
    }

    /**
     * Metodo para escribri un archivo CSV (texto)
     * @param lista
     * @param ruta
     * @throws IOException
     */
    public void escribirCSV(List<Producto> lista, String ruta) throws IOException{
        BufferedWriter bw = null;
        try{
            bw = new BufferedWriter(new FileWriter(ruta));
            for(Producto p : lista){
                bw.write(p.getId()+","+p.getNombre()+","+p.getPrecio());
                bw.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(bw != null){
                bw.close();
            }
        }
    }

    public List<Alumno> leerCSVAlumno(String ruta)throws IOException{
        List<Alumno> lista = new ArrayList<>();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(ruta));
            String linea;
            while((linea = br.readLine() )!= null){
                String[] partesLista = linea.split(",");
                int idAlumno = Integer.parseInt(partesLista[0]);
                String nombreAlumno = partesLista[1];
                double notaAlumno = Double.parseDouble(partesLista[2]);

                lista.add(new Alumno(idAlumno, nombreAlumno, notaAlumno));
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            if(br != null){
                br.close();
            }
        }
        return lista;
    }

    public void escribirCSVAlumno(List<Alumno> lista, String ruta)throws IOException{
        BufferedWriter bw = null;
        try{
            bw = new BufferedWriter(new FileWriter(ruta));
            for(Alumno a : lista){
                bw.write(a.getIdAlumno()+","+a.getNombreAlumno()+","+a.getNotaAlumno());
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            if(bw != null){
                bw.close();
            }
        }
    }
}
