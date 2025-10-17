import java.io.File;

    /**
     * 1.2 Instancia un directorio y determina si existe o no existe.
     * Muestra su nombre, longitud y ruta absoluta
     */
    public class Ejercicio12 {
        public static void main(String[] args) {

            File archivo2 = new File("carpeta12");
            // Conditional
            if(archivo2.exists()){
                System.out.println("La carpeta existe\nNombre: "+archivo2.getName()+"\nLongitud: "+archivo2.length()+"\nRuta: "+archivo2.getAbsolutePath()+"\n");
            }else{
                System.out.println("La carpeta no existe");
            }
        }
    }
