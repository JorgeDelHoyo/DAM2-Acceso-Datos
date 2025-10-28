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

    public List<Alumno> leerAleatorioAlumno(String ruta)throws IOException{
        List<Alumno> lista = new ArrayList<>();
        RandomAccessFile raf = null;
        try{
            raf = new RandomAccessFile(ruta,"rw");
            while(raf.getFilePointer() < raf.length()){
                int idAlumno = raf.readInt();
                char[] nombreAlumnoChars = new char[10];
                for(int i = 0; i < nombreAlumnoChars.length; i++){
                    nombreAlumnoChars[i] = raf.readChar();
                }
                String nombreAlumno = new String(nombreAlumnoChars);

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

    private final int TAMAÑO_REGISTRO = 4+(10*2)+8;

    public void escribirAleatorioPorID(List<Alumno> lista,String ruta, int idAlumno)throws IOException{
        RandomAccessFile raf = null;
        try{

            raf = new RandomAccessFile(ruta, "rw");

            raf.setLength(0);

            for(int i = idAlumno-1; i  < lista.size(); i++){
                Alumno a = lista.get(i);
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

}
