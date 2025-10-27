package ConfiguracionMusico;

import Objetos.Musico;
import Interfaces.MusicoRepositorio;
import Archivos.*;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.*;

public class MusicoConfig {

    // 'repo' es la implementación concreta de persistencia (CSV, XML o binario)
    private MusicoRepositorio repo;
    // Lista de músicos cargada en memoria durante la ejecución
    private List<Musico> memoria = new ArrayList<>();

    public MusicoConfig(String tipoParametro, String rutaParametro) {
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream("Configuracion/config.properties")) {
            p.load(fis);
        } catch (Exception e) {
            System.out.println("No se encontró config.properties. Usando CSV por defecto.");
        }

        // Usa los parámetros si se pasaron, sino busca en propiedades, sino usa valores por defecto
        String tipo = (tipoParametro != null) ? tipoParametro :
                p.getProperty("persistence", "csv").toLowerCase();

        String ruta = (rutaParametro != null) ? rutaParametro :
                p.getProperty("ruta", "data/musicos.csv");

        switch (tipo) {
            case "xml" -> repo = new FicheroXML(ruta);
            case "binario" -> repo = new FicheroBinario(ruta);
            default -> repo = new FicheroCSV(ruta);
        }
    }

    /**
     * Carga la lista de músicos desde el repositorio y la guarda en memoria
     * @throws Exception
     */
    public void cargar() throws Exception {
        memoria = repo.cargar();
    }

    /**
     * Guarda la lista de músicos en el repositorio configurado
     * @throws Exception
     */
    public void guardar() throws Exception {
        repo.guardar(memoria);
    }

    /**
     * Devuelve la lista de músicos en memoria
     * @return
     */
    public List<Musico> listar() {
        return memoria;
    }

    /**
     * Añade un músico, reemplazando cualquier músico con el mismo ID
     * @param m
     */
    public void agregar(Musico m) {
        memoria.removeIf(x -> x.getIdMusico() == m.getIdMusico());
        memoria.add(m);
    }

    /**
     * Busca un músico por su ID en la lista de memoria
     * @param id
     * @return Devuelve null si no lo encuentra
     */
    public Musico buscar(int id) {
        return memoria.stream().filter(m -> m.getIdMusico() == id).findFirst().orElse(null);
    }

    /**
     * Elimina un músico por su ID
     * @param id
     * @return devuelve true si se eliminó, false si no existía
     */
    public boolean eliminar(int id) {
        return memoria.removeIf(m -> m.getIdMusico() == id);
    }

    /**
     * Devuelve la ruta del archivo o repositorio que se está usando
     * @return
     */
    public String getRuta() {
        return repo.getRuta();
    }
}
