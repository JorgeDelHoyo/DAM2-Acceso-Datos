import ConfiguracionMusico.MusicoConfig;
import Interfaces.MusicoRepositorio;
import Menus.MenuOpciones;
import service.BandaService;
import service.MusicoService;
import util.DatabaseManager;
import util.SetupDatabase;
import repository.MusicoRepositorioJDBC;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   BIENVENIDO AL GESTOR DE MÚSICOS   ");
        System.out.println("=====================================");

        try {
            // 1. Configurar el repositorio a través de la fábrica
            MusicoConfig config = new MusicoConfig();
            MusicoRepositorio musicoRepositorio = config.getRepositorio();

            // Si el repositorio es JDBC, inicializamos la base de datos
            if (musicoRepositorio instanceof MusicoRepositorioJDBC) {
                System.out.println("Inicializando base de datos...");
                SetupDatabase.inicializar();
            }

            // 2. Inyectar el repositorio en los servicios
            MusicoService musicoService = new MusicoService(musicoRepositorio);
            BandaService bandaService = new BandaService(); // Sigue usando JDBC

            // 3. Iniciar el menú de opciones
            MenuOpciones menu = new MenuOpciones(musicoService, bandaService);
            menu.mostrarMenu();

            System.out.println("\nGracias por usar el Gestor de Músicos. ¡Hasta luego!");

        } catch (Exception e) {
            System.err.println("Error fatal al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cerramos la conexión solo si se usó JDBC
                DatabaseManager.cerrarConexion();
                System.out.println("\nConexión a la base de datos cerrada (si se usó).");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
