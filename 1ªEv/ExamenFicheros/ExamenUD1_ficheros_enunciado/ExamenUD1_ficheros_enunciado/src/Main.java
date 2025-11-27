import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        GestorPaises gp = new GestorPaises();

        // Ejercicio 1: Leer desde un CSV
        List<Pais> paisesCSV = gp.leerCSV("ExamenUD1_ficheros_enunciado/paises.csv");
        List<Pais> paisesDAT = gp.leerDAT("ExamenUD1_ficheros_enunciado/paises.dat");


        // Ejercicio 2: Leer desde un Aleatorio
        System.out.println("\n=========================ALEATORIO=========================");
        gp.leerAleatorio("ExamenUD1_ficheros_enunciado/paises.dat",6);
        gp.leerAleatorio("ExamenUD1_ficheros_enunciado/paises.dat",7);
        gp.leerAleatorio("ExamenUD1_ficheros_enunciado/paises.dat",8);
        gp.leerAleatorio("ExamenUD1_ficheros_enunciado/paises.dat",9);
        gp.leerAleatorio("ExamenUD1_ficheros_enunciado/paises.dat",10);

        // Comprobación ejercicios 1 y 2. Deben de aparecer 10 paises
        System.out.println("\n=========================Paises============================");
        gp.mostrarPaises(paisesDAT);

        // Ejercicio 3: Escribir información de memoria a XML
        gp.escribeXML(paisesDAT,"ExamenUD1_ficheros_enunciado/paises.xml");
    }
}
