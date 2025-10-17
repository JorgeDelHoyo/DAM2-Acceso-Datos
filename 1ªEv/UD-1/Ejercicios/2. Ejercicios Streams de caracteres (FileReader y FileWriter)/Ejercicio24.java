import java.io.*;
import java.util.Scanner;

/**
 * 2.4 Escribe una clase Java que pida una serie de frases por teclado hasta que se inserte como frase la palabra “fin”.
 * Dichas frases deberán guardarse en un fichero de texto.
 * A continuación, el programa visualizará el contenido del fichero, frase por frase.
 */
public class Ejercicio24 {

    public static void main(String[] args) throws IOException {
        // Variable para determinar el nombre del fichero
        String nombreFichero = "ejercicio24.txt";
        // Objeto scanner para recoger por teclado las frases
        Scanner sc = new Scanner(System.in);
        // Instanciamos FileWriter
        FileWriter fw = new FileWriter(nombreFichero);

        // Pedimos al usuario las frases
        System.out.println("Introduce una serie de frases\nIntroduce 'fin' para finalizar");
        String frase = sc.nextLine(); // Recogemos la frase

        while (!frase.equalsIgnoreCase("fin")) {
            fw.write(frase+"\n");
            frase = sc.nextLine(); // Recogemos la frase
        }

        fw.close(); // Liberamos recursos

        // Inicializamos FileReader para leer el archivo
        FileReader fr = new FileReader(nombreFichero);
        int letraASCII; // Variable de cada caracter en ASCII
        // Condicion que mientras exista el caracter lo muestre
        while ((letraASCII = fr.read()) != -1) {
            System.out.print((char) letraASCII);
        }
    }
}
