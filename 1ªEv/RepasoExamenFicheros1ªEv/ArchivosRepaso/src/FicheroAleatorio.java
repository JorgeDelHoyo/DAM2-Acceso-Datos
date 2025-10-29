import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FicheroAleatorio {

    /**
     * Leo fichero aleatorio
     * @param ruta
     * @return
     * @throws IOException
     */
    public List<Producto> leerAleatorio(String ruta)throws IOException{
        List<Producto> lista = new ArrayList<>();
        RandomAccessFile raf = null;
        try{
            raf = new RandomAccessFile(ruta,"rw");
            while(raf.getFilePointer() < raf.length()){
                int id = raf.readInt();

                char[] nombreChars = new char[20];
                for(int i = 0; i < nombreChars.length; i++){
                    nombreChars[i] = raf.readChar();
                }
                String nombre = new String(nombreChars);

                double precio = raf.readDouble();

                lista.add(new Producto(id,nombre,precio));
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }finally {
            if(raf != null){
                raf.close();
            }
        }
        return lista;
    }

    /**
     * Metodo para escribir un Fichero Aleatorio
     * @param lista
     * @param ruta
     * @throws IOException
     */
    public void escribirAleatorio(List<Producto> lista, String ruta) throws IOException{
        RandomAccessFile raf = null;
        try{
            raf = new RandomAccessFile(ruta,"rw");
            for(Producto p : lista){
                raf.writeInt(p.getId());
                StringBuffer sb = new StringBuffer(p.getNombre());
                sb.setLength(20);
                raf.writeChars(sb.toString());
                raf.writeDouble(p.getPrecio());
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            if(raf != null){
                raf.close();
            }
        }
    }

    private final int TAMAÑO_REGISTRO = 4+(10*2)+8; // INT+CHAR+DOUBLE

    public List<Alumno> leerAleatorioAlumnosPorID(String ruta, int id)throws IOException{
        List<Alumno> lista = new ArrayList<>();
        RandomAccessFile raf = null;
        try{
            raf = new RandomAccessFile(ruta,"rw");
            long posicionAlumno = (id-1) * TAMAÑO_REGISTRO;
            if(posicionAlumno >= raf.length()){
                System.out.println("Id fuera de rango");
            }
            raf.seek(posicionAlumno);

            int idAlumno = raf.readInt();
            char[] nombreChars = new char[10];
            for(int i = 0; i < nombreChars.length; i++){
                nombreChars[i] = raf.readChar();
            }
            String nombreAlumno = new String(nombreChars);
            double notaAlumno = raf.readDouble();

            lista.add(new Alumno(idAlumno,nombreAlumno,notaAlumno));

        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            if(raf != null){
                raf.close();
            }
        }
        return lista;
    }

    public List<Alumno> leerAleatorioAlumnosAPartirID(String ruta, int id)throws IOException{
        List<Alumno> lista = new ArrayList<>();
        RandomAccessFile raf = null;
        try{
            raf = new RandomAccessFile(ruta,"rw");
            long posicionAlumno = (long) (id-1)*TAMAÑO_REGISTRO;
            if(posicionAlumno >= raf.length()){
                System.out.println("Id fuera de rango");
            }
            raf.seek(posicionAlumno);
            while(raf.getFilePointer() < raf.length()){
                int idAlumno = raf.readInt();
                char[] nombreCharsAlumno = new char[10];
                for(int i = 0; i < nombreCharsAlumno.length;i++){
                    nombreCharsAlumno[i] = raf.readChar();
                }
                String nombreAlumno = new String(nombreCharsAlumno);
                double notaAlumno = raf.readDouble();

                lista.add(new Alumno(idAlumno,nombreAlumno,notaAlumno));
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            if(raf != null){
                raf.close();
            }
        }
        return lista;
    }

    public void escribirAleatorioAlumno(List<Alumno>lista, String ruta)throws IOException{
        RandomAccessFile raf = null;
        try{
            raf = new RandomAccessFile(ruta, "rw");
            for(Alumno a : lista){
                raf.writeInt(a.getIdAlumno());
                StringBuffer sb = new StringBuffer(a.getNombreAlumno());
                sb.setLength(10);
                raf.writeChars(sb.toString());
                raf.writeDouble(a.getNotaAlumno());
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            if(raf != null){
                raf.close();
            }
        }
    }

    public void escribirAleatorioAlumnoAPartirID(List<Alumno>lista, String ruta,int id)throws IOException{
        RandomAccessFile raf = null;
        try{
            raf = new RandomAccessFile(ruta,"rw");
            long posicionAlumno = (id-1)*TAMAÑO_REGISTRO;
            if(posicionAlumno >= raf.length()){
                System.out.println("Id fuera de rango");
            }
            raf.seek(posicionAlumno);
            for (Alumno a : lista) {
                raf.writeInt(a.getIdAlumno());
                StringBuffer sb = new StringBuffer(a.getNombreAlumno());
                sb.setLength(10); // tamaño fijo
                raf.writeChars(sb.toString());
                raf.writeDouble(a.getNotaAlumno());
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            if(raf != null){
                raf.close();
            }
        }
    }

    public void escribirAleatorioAlumnoID(List<Alumno>lista,String ruta, int id) throws IOException {
        RandomAccessFile raf = null;

        try{
            raf = new RandomAccessFile(ruta,"rw");
            long posicion = (id-1)*TAMAÑO_REGISTRO;
            raf.seek(posicion);
            for(Alumno a : lista){
                if(a.getIdAlumno() == id){
                    raf.writeInt(a.getIdAlumno());
                    StringBuffer sb = new StringBuffer(a.getNombreAlumno());
                    sb.setLength(10);
                    raf.writeChars(sb.toString());
                    raf.writeDouble(a.getNotaAlumno());
                    break;
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            if(raf != null){
                raf.close();
            }
        }
    }
}
