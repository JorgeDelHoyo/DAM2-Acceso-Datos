package ConfiguracionMusico;

import Archivos.FicheroBinario;
import Archivos.FicheroCSV;
import Archivos.FicheroXML;
import Archivos.MusicoRepositorioJDBC;
import Interfaces.MusicoRepositorio;
import Objetos.Musico;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class MusicoConfig {

    private MusicoRepositorio repo;

    public MusicoConfig() {
        Properties p = new Properties();
        // Se carga el fichero como un recurso del classpath para evitar problemas con las rutas relativas.
        try (InputStream input = MusicoConfig.class.getResourceAsStream("/Configuracion/config.properties")) {
            if (input == null) {
                System.err.println("No se pudo encontrar 'config.properties'. Usando valores por defecto.");
            } else {
                p.load(input);
            }
        } catch (Exception e) {
            System.err.println("Error al leer config.properties: " + e.getMessage());
        }

        String tipo = p.getProperty("persistence.type", "csv").toLowerCase();

        switch (tipo) {
            case "jdbc":
                String dbUrl = p.getProperty("db.url", "jdbc:mysql://localhost:3306/musicos_db");
                String dbUser = p.getProperty("db.user", "root");
                String dbPassword = p.getProperty("db.password", "");
                repo = new MusicoRepositorioJDBC(dbUrl, dbUser, dbPassword);
                break;
            case "xml":
                repo = new FicheroXML(p.getProperty("file.path", "data/musicos.xml"));
                break;
            case "binario":
                repo = new FicheroBinario(p.getProperty("file.path", "data/musicos.bin"));
                break;
            case "csv":
            default:
                repo = new FicheroCSV(p.getProperty("file.path", "data/musicos.csv"));
                break;
        }
    }

    public List<Musico> listarTodos() throws Exception {
        return repo.cargar();
    }

    public Musico agregar(Musico m) throws Exception {
        return repo.añadir(m);
    }

    public Optional<Musico> buscar(int id) throws Exception {
        return repo.buscarPorId(id);
    }

    public boolean modificar(Musico m) throws Exception {
        return repo.modificar(m);
    }

    public boolean eliminar(int id) throws Exception {
        return repo.eliminar(id);
    }
    
    public void guardarTodos(List<Musico> musicos) throws Exception {
        repo.guardar(musicos);
    }

    public String getRuta() {
        return repo.getRuta();
    }
}
