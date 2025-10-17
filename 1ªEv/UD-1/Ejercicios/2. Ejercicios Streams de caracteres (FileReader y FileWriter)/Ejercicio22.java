import java.io.FileReader;
import java.io.IOException;

/**
 * 2.2 Crea una clase de Java que permita buscar una vocal en un fichero de texto y muestre el número de veces que se repite.
 */
public class Ejercicio22 {

    public static void main(String[] args) throws IOException {

        String nombreFichero = "ejemplo21.txt";

        FileReader fr = new FileReader(nombreFichero);

        int letraASCII;
        int contadorA = 0;
        int contadorE = 0;
        int contadorI = 0;
        int contadorO = 0;
        int contadorU = 0;
        while ((letraASCII = fr.read()) != -1) {
            char letra = (char) letraASCII;
            switch (Character.toLowerCase(letra)) {
                case 'a' -> contadorA++;
                case 'e' -> contadorE++;
                case 'i' -> contadorI++;
                case 'o' -> contadorO++;
                case 'u' -> contadorU++;
            }
        }
        System.out.println("A: " + contadorA + " veces.");
        System.out.println("E: " + contadorE + " veces.");
        System.out.println("I: " + contadorI + " veces.");
        System.out.println("O: " + contadorO + " veces.");
        System.out.println("U: " + contadorU + " veces.");
    }
}