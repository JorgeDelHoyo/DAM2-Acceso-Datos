import java.io.File;

public class Ejercicio18 {
    /**
     * 1.8 Lista todos los archivos de una carpeta y sus subcarpetas
     * con su ruta desde la ubicación actual. No deben aparecer las carpetas
     */
    public static void main(String[] args) {

        // Nombre de la carpeta a mostrar
        String nombreDirectorio = "carpeta18";
        // Instanciamos y llamamos a los directorios
        mostrarContenido(new File(nombreDirectorio));


    }

    /**
     * Metodo recursivo para mostrar el contenido de una carpeta
     */
    public static void mostrarContenido(File nombreCarpeta){
        // Recibo la carpeta y recojo los nombres que devuelve un array de archivos
        File[] contenido = nombreCarpeta.listFiles();

        // Recorro todo el array

        for(File f: contenido){
            // Determinar si es carpeta o no
            if(f.isDirectory()){
                System.out.println("/"+f.getName()+"/");
                mostrarContenido(f);
            }else{
                System.out.println("-"+f.getName()+"/");
            }
            // Si es carpeta vuelvo a llamar a esta funcion
            // Si es ficheor muestro su nombre
        }
    }
}
