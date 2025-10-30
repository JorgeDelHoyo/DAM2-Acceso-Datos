import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class ExamenPruebaFicheroAleatorio {

    private final int TAMANIO_REGISTRO2 = 33; // id + nombre + notaMaxima + fecha + aprobado

    public List<ExamenPrueba> leerAleatorio(String ruta)throws IOException {
        List<ExamenPrueba> lista = new ArrayList<>();
        RandomAccessFile raf = null;
        try{
            raf = new RandomAccessFile(ruta, "rw");
            while(raf.getFilePointer() < raf.length()){
                int id = raf.readInt();
                char[]nombreSeparado = new char[5];
                for(int i = 0; i < nombreSeparado.length; i++){
                    nombreSeparado[i] = raf.readChar();
                }
                String nombre = new String(nombreSeparado);
                float notaMaxima = raf.readFloat();
                char[]fechaSeparado = new char[7];
                for(int i = 0; i < fechaSeparado.length; i++){
                    fechaSeparado[i] = raf.readChar();
                }
                String fecha = new String(fechaSeparado);
                boolean aprobado = raf.readBoolean();

                lista.add(new ExamenPrueba(id,nombre,notaMaxima,fecha,aprobado));
            }
        }catch (FileNotFoundException e){
            System.out.println("No se encontro el archivo");
        }finally {
            if (raf != null) {
                raf.close();
            }
        }
        return lista;
    }

    public void escribirAleatorio(List<ExamenPrueba> lista, String ruta)throws IOException {
        RandomAccessFile raf = null;
        try{
            raf = new RandomAccessFile(ruta, "rw");
            //for(ExamenPrueba e: lista){
                //raf.writeInt(e.getId());
                //StringBuffer sb = new StringBuffer(e.getNombre());
                //sb.setLength(5);
                //raf.writeChars(sb.toString());
                //raf.writeFloat(e.getNotaMaxima());
                //StringBuffer sb2 = new StringBuffer(e.getFecha());
               // sb2.setLength(7);
               // raf.writeChars(sb2.toString());
                //raf.writeBoolean(e.isAprobado());
            //}
        }catch (FileNotFoundException e){
            System.out.println("No se encontro el archivo");
        }finally {
            if (raf != null) {
                raf.close();
            }
        }
    }

    public List<ExamenPrueba> leerAleatorioID(String ruta, int id)throws IOException{
        List<ExamenPrueba> lista = new ArrayList<>();
        RandomAccessFile raf = null;
        try{
            raf = new RandomAccessFile(ruta, "rw");
            long posicionExamen = (long) (id-1)*TAMANIO_REGISTRO2;
            if(posicionExamen >= raf.length()){
                System.out.println("Indice fuera de rango");
            }else{
                raf.seek(posicionExamen);
                int idExamen = raf.readInt();
                char[]nombreSeparado = new char[5];
                for(int i = 0; i <nombreSeparado.length; i++){
                    nombreSeparado[i] = raf.readChar();
                }
                String nombre = new String(nombreSeparado);
                float notaMaxima = raf.readFloat();
                char[]fechaSeparado = new char[7];
                for(int i = 0; i <fechaSeparado.length; i++){
                    fechaSeparado[i] = raf.readChar();
                }
                String fecha = new String(fechaSeparado);
                boolean aprobado = raf.readBoolean();

                lista.add(new ExamenPrueba(idExamen,nombre,notaMaxima,fecha,aprobado));
            }
        }catch (FileNotFoundException e){
            System.out.println("No se encontro el archivo");
        }finally {
            if (raf != null) {
                raf.close();
            }
        }
        return  lista;
    }

    public void escribirAleatorioID(List<ExamenPrueba>lista, String ruta, int id)throws IOException{
        RandomAccessFile raf = null;
        try{
            raf = new RandomAccessFile(ruta, "rw");
            long  posicionExamen = (long) (id-1)*TAMANIO_REGISTRO2;

            raf.seek(posicionExamen); // Te posiciona donde debe
            raf.setLength(0); // SOLO ESCRIBE EL DESEADO
                for(ExamenPrueba e: lista){
                    if(e.getId() == id){
                        raf.writeInt(e.getId());
                        StringBuffer sb = new StringBuffer(e.getNombre());
                        sb.setLength(5);
                        raf.writeChars(sb.toString());
                        raf.writeFloat(e.getNotaMaxima());
                        StringBuffer sb2 = new StringBuffer(e.getFecha());
                        sb2.setLength(7);
                        raf.writeChars(sb2.toString());
                        raf.writeBoolean(e.isAprobado());
                        break;
                    }
                }
        }catch (FileNotFoundException e){
            System.out.println("No se encontro el archivo");
        }finally {
            if(raf != null){
                raf.close();
            }
        }
    }

    public void escribirAleatorioAPartirID(List<ExamenPrueba>lista, String ruta, int id)throws IOException{
        RandomAccessFile raf = null;
        try{
            raf = new RandomAccessFile(ruta, "rw");
            long  posicionExamen = (long) (id-1)*TAMANIO_REGISTRO2;

            raf.seek(posicionExamen);
            raf.setLength(0);
            for(ExamenPrueba e: lista){
                if(e.getId() >= id){
                    raf.writeInt(e.getId());
                    StringBuffer sb = new StringBuffer(e.getNombre());
                    sb.setLength(5);
                    raf.writeChars(sb.toString());
                    raf.writeFloat(e.getNotaMaxima());
                    StringBuffer sb2 = new StringBuffer(e.getFecha());
                    sb2.setLength(7);
                    raf.writeChars(sb2.toString());
                    raf.writeBoolean(e.isAprobado());
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("No se encontro el archivo");
        }finally {
            if(raf != null){
                raf.close();
            }
        }
    }
}
