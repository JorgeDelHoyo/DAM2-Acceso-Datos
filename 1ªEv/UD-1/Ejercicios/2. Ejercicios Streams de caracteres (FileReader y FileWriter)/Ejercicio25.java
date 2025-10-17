import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 2.5 Una clase que dado un archivo .java cambie los tabuladores por 2 espacios (\t es un tabulador)
 */
public class Ejercicio25 {
    public static void main(String[] args) throws IOException {

        // Abro este archivo en modo LECTURA
        FileReader fr = new FileReader("ejemplo20_original.txt");

        // Abro otro archivo en modo ESCRITURA
        FileWriter fw = new FileWriter("ejemplo20_nuevo.txt");

        // Recorro el archivo de lectura
        int letra;
        while( (letra = fr.read()) != -1){
            System.out.println((char)letra);
        }

        // Cierro el stream de lectura
        fr.close();
    }
}
