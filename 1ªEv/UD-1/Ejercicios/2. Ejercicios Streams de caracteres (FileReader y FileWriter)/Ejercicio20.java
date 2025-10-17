import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ejercicio20 {

    public static void main(String[] args) throws IOException {

        // Generamos el archivo y lo escribimos en un fichero

        // Datos del fichero y su contenido
        String nombreFichero = "ejemplo20_original.txt";
        String frase ="Hola buenos dias";

        // Instancio un FileWriter para escribir en un fichero de texto
        FileWriter fw = new FileWriter(nombreFichero);

        // Escribo la frase
        fw.write(frase);

        // Cierro el FileWriter y libero recursos
        fw.close();

        // Leo el fichero
        FileReader fr = new FileReader(nombreFichero);

        int letra;
        while((letra = fr.read()) != -1){
            System.out.print((char)letra);
        }

        // Cierro el FileReader y libero recursos
        fr.close();

    }
}
