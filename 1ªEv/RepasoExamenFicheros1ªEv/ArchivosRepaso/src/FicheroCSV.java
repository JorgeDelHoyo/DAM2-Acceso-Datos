import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroCSV {

    public List<Producto> leerCSV(String ruta) throws IOException{
        List<Producto> productos = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
            String linea;
            while((linea = br.readLine()) != null){
                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                double precio = Double.parseDouble(partes[2]);

                Producto p = new Producto(id,nombre, precio);
                productos.add(p);
            }
        }
        return productos;
    }

    public void escribirCSV(List<Producto> lista, String ruta) throws IOException{
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))){
            for(Producto p : lista){
                bw.write(p.getId()+","+p.getNombre()+","+p.getPrecio());
                bw.newLine();
            }
        }
    }
}
