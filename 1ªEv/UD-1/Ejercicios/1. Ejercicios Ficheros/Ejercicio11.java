import java.io.File;

    /**
     * 1.1 Instancia un fichero y determina si existe o no existe.
     * Muestra su nombre, longitud y ruta absoluta
     *
     */
    public class Ejercicio11 {
        public static void main(String[] args){

            // Declare archivo1
            File archivo1 = new File("ejemplo1.txt");
            // Conditional
            if(archivo1.exists()){
                System.out.println("El archivo existe\nNombre: "+archivo1.getName()+"\nLongitud: "+archivo1.length()+"\nRuta: "+archivo1.getAbsolutePath()+"\n");
            }else{
                System.out.println("El archivo no existe");
            }
        }
    }