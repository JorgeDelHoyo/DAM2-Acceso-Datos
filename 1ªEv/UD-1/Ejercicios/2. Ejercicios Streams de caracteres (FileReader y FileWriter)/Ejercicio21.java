import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 2.1 Crea un fichero de texto con algún editor de texto y después realiza una clase de Java que visualice su contenido.
 * El programa recibe el nombre del archivo en tiempo de ejecución por línea de comandos.
 */
public class Ejercicio21 {

    public static void main(String[] args) throws IOException {

        // Declaramos el nombre del fichero
        String nombreFichero = "ejemplo21.txt";
        // Declaramos el texto a introducir
        String texto = "Ejercicio21 texto de ejemplo";
        // Instanciamos el FileWriter para escribir en el documento
        FileWriter fw = new FileWriter(nombreFichero);

        // Escribimos el texto con FileWriter
        fw.write(texto);

        // Liberamos recursos
        fw.close();

        // Instanciamos el FileReader para leer el documento
        FileReader fr = new FileReader(nombreFichero);
        // Variable para valores ASCII
        int letra;

        // Mientras la letra sea igual a el caracter ASCII del valor que lee FileReader
        while ((letra = fr.read()) != -1) {
            // Muestra la letra casteando el valor ASCII a caracter
            System.out.print((char) letra);
        }
    }
}
