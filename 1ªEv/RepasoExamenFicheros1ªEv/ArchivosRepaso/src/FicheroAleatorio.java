import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FicheroAleatorio {

    public void escribirAleatorio(List<Producto> lista, String ruta) throws Exception{
        try (RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            for(Producto p : lista){
                raf.writeInt(p.getId());
                StringBuffer sb = new StringBuffer(p.getNombre());
                sb.setLength(20);
                raf.writeChars(sb.toString());
                raf.writeDouble(p.getPrecio());
            }
        }
    }

    public List<Producto> leerAleatorio(String ruta) throws Exception{
        List<Producto> lista = new ArrayList<>();
        try(RandomAccessFile raf = new RandomAccessFile(ruta, "rw")){
            while(raf.getFilePointer() < raf.length()){
                int id = raf.readInt();
                char[] nombreChars = new char[20];
                for(int i = 0; i < nombreChars.length; i++) {

                    nombreChars[i] = raf.readChar(); // Leer cada char real
                }
                String nombre = new String(nombreChars).trim();

                double precio = raf.readDouble();

                lista.add(new Producto(id, nombre, precio));
            }
            return lista;
        }
    }
}
