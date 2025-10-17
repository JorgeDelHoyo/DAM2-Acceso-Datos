import java.io.File;
import java.util.Scanner;

    /**
     * 1.4 Crea una carpeta y determina si se ha creado o si ya existía.
     */
    public class Ejercicio14 {
        public static void main(String[] args) {

            // Recogemos el nombre de la carpeta
            Scanner sc = new Scanner(System.in);
            System.out.println("Introduce nombre de carpeta a crear");
            String nombreCarpeta = sc.nextLine();


            //File fichero4 = new File("carpeta14");

            File fichero4 = new File(nombreCarpeta);

            // Condiciones
            if(fichero4.mkdir()){
                System.out.println("El directorio se ha creado");
            }else{
                System.out.println("El directorio ya estaba creado");
            }
        }
    }
