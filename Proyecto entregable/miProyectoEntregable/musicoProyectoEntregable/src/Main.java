
import ConfiguracionMusico.MusicoConfig;
import Menus.MenuOpciones;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   BIENVENIDO AL GESTOR DE MÚSICOS   ");
        System.out.println("=====================================");

        try {
            // Inicializa la configuración de músicos (selecciona repositorio automáticamente)
            MusicoConfig config = new MusicoConfig("dat", "data/musicos.dat");

            System.out.println("Repositorio cargado: " + config.getRuta());

            // Lanza el menú de opciones
            MenuOpciones menu = new MenuOpciones(config);
            menu.mostrarMenu();

            System.out.println("\nGracias por usar el Gestor de Músicos. ¡Hasta luego!");
        } catch (Exception e) {
            System.out.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
