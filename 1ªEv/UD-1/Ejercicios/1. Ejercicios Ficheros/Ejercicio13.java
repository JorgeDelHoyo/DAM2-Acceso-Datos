import java.io.File;
import java.io.IOException;

    /**
     * 1.3 Crea un fichero y determina si se ha creado o si ya existía.
     *
     * Versión con scanner
     *
     * Versión con paso de parámetros en tiempo de ejecución
     */
    public class Ejercicio13 {
        public static void main(String[] args) throws IOException {

            File fichero3 = new File("ejemplo13.txt");
            if (fichero3.createNewFile()) {
                System.out.println("EL fichero se ha creado");
            } else {
                System.out.println("El fichero ya existia");
            }
        }
    }
