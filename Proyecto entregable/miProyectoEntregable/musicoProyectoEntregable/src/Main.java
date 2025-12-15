import ConfiguracionMusico.MusicoConfig;
import Menus.MenuOpciones;

public class Main {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   BIENVENIDO AL GESTOR DE MÚSICOS   ");
        System.out.println("=====================================");

        try {
            // Inicializa la configuración de músicos.
            // La clase MusicoConfig ahora lee la configuración desde 'config.properties'
            // y selecciona el repositorio (JDBC, XML, etc.) automáticamente.
            MusicoConfig config = new MusicoConfig();

            System.out.println("Repositorio seleccionado: " + config.getRuta());

            // Lanza el menú de opciones
            MenuOpciones menu = new MenuOpciones(config);
            menu.mostrarMenu();

            System.out.println("\nGracias por usar el Gestor de Músicos. ¡Hasta luego!");
        } catch (Exception e) {
            System.err.println("Error fatal al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
