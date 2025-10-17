import java.io.File;
import java.util.Scanner;

    /**
     * 1.5 Elimina un fichero y determina si se ha eliminado o no
     * 1.6 Elimina un directorio y determina si se ha eliminado o no (sólo se puede eliminar un directorio si esta vacío)
     */
    public class Ejercicio15_16 {
        public static void main(String[] args) {

            // Recogemos el nombre de la del ficheor a borrar
            Scanner sc = new Scanner(System.in);
            System.out.println("Introduce nombre del archivo a borrar");
            String nombreFichero = sc.nextLine();

            File ficheroBorrar = new File(nombreFichero);
            if(ficheroBorrar.delete())  {
                System.out.println("Borrado correctamente");
            }else{
                System.out.println("No habia nada que borrar");
            }
        }
    }
