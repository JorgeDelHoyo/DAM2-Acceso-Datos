import java.io.FileWriter;
import java.io.IOException;

/**
 * 2.3 Crea una clase de Java que cree ‘n’ archivos, nombre1.txt, nombre2.txt,.... nombreN.txt.
 * El contenido de cada archivo contendrá la frase “Este es el fichero nombreN.txt”.
 */
public class Ejercicio23 {
    public static void main(String[] args) throws IOException {

        // Variable para determinar cuantos archivos creamos
        int numeroArchivos = 2;
        // Variable fileWriter
        FileWriter fw;

        // Bucle que itera hasta el numero de veces que queremos crear
        for (int i = 1; i <= 2; i++) {
            // Nombre del fichero según su iteración
            String nombreFichero = "nombre" + i + ".txt";
            // Creamos el fichero
            fw = new FileWriter(nombreFichero);
            // Escribimos en el fichero
            fw.write("Este es el fichero " + nombreFichero);
            // Liberamos recursos
            fw.close();
        }
    }
}
