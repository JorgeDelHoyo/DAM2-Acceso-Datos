import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 2.6 Calcular la media de los números almacenados en un fichero. Cada número está en una línea.
 * El nombre del fichero se pasa como argumento de la línea de comandos
 */
public class Ejercicio26 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner("numeros.txt");

        var listaNumeros = new ArrayList<>();

        int letra;
        while ((letra = sc.nextLine()) != -1){
            listaNumeros.add(Integer.parseInt(String.valueOf(letra)));
        }

        listaNumeros.forEach(letra);
    }
}
