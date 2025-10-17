import java.io.File;

    /**
     * 1.7 Dado un directorio muestra el nombre de todos los archivos que contenga, o si está vacío
     */
    public class Ejercicio17 {
        public static void main(String[] args) {

            // Ejemplo con la carpeta Unidad-1/src
            String nombreDirectorio = "C:/Users/delhoyoballestin23/IdeaProjects/Unidad-1/src";

            File directorio = new File(nombreDirectorio);

            // Comprobamos que es un directorio existente
            if (directorio.isDirectory()) {
                //Necesito listar su contenido
                String[] listarFicheros = directorio.list();
                for (String s : listarFicheros) {
                    System.out.println(s);
                }
            } else {
                System.out.println("Proporciona el nombre de un directorio valido");
            }
        }
    }