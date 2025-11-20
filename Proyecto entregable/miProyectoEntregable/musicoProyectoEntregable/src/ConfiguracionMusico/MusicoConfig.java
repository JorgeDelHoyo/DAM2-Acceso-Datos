package ConfiguracionMusico;

import Interfaces.MusicoRepositorio;
import Archivos.FicheroBinario;
import Archivos.FicheroCSV;
import Archivos.FicheroXML;
import repository.MusicoRepositorioJDBC;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Fábrica de Repositorios.
 * Lee la configuración y proporciona la implementación de persistencia adecuada.
 */
public class MusicoConfig {

    private final MusicoRepositorio repositorio;

    public MusicoConfig() {
        Properties p = new Properties();
        String tipo = "jdbc"; // Valor por defecto
        String ruta = "data/musicos.dat"; // Ruta por defecto para ficheros

        try (FileInputStream fis = new FileInputStream("Configuracion/config.properties")) {
            p.load(fis);
            tipo = p.getProperty("persistence.type", "jdbc").toLowerCase();
            ruta = p.getProperty("persistence.path", "data/musicos.dat");
        } catch (Exception e) {
            System.out.println("ADVERTENCIA: No se encontró config.properties. Usando la implementación JDBC por defecto.");
        }

        // Selecciona la implementación del repositorio según la configuración
        switch (tipo) {
            case "binario":
                System.out.println("Usando repositorio de Fichero Binario.");
                this.repositorio = new FicheroBinario(ruta);
                break;
            case "csv":
                System.out.println("Usando repositorio de Fichero CSV.");
                this.repositorio = new FicheroCSV(ruta);
                break;
            case "xml":
                System.out.println("Usando repositorio de Fichero XML.");
                this.repositorio = new FicheroXML(ruta);
                break;
            case "jdbc":
            default:
                System.out.println("Usando repositorio JDBC.");
                this.repositorio = new MusicoRepositorioJDBC();
                break;
        }
    }

    /**
     * Devuelve la instancia del repositorio configurado.
     * @return una implementación de MusicoRepositorio.
     */
    public MusicoRepositorio getRepositorio() {
        return this.repositorio;
    }
}
