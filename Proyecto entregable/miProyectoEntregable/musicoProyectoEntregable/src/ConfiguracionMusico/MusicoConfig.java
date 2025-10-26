package ConfiguracionMusico;

import Objetos.Musico;
import Interfaces.MusicoRepositorio;
import Archivos.*;

import java.io.FileInputStream;
import java.util.*;

public class MusicoConfig {

    private MusicoRepositorio repo;
    private List<Musico> memoria = new ArrayList<>();
    // 'repo' es la implementación concreta de persistencia (CSV, XML o binario)
    // 'memoria' es la lista de músicos cargada en memoria durante la ejecución

    public MusicoConfig() {
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream("Configuracion/config.properties")) {
            p.load(fis);
            // Intenta leer el archivo de configuración para decidir tipo de persistencia y ruta
        } catch (Exception e) {
            System.out.println("No se encontró config.properties. Usando CSV por defecto.");
            // Si no encuentra el archivo, usa CSV como predeterminado
        }

        String tipo = p.getProperty("persistence", "csv").toLowerCase();
        String ruta = p.getProperty("ruta", "data/musicos.csv");
        // Obtiene el tipo de persistencia y la ruta del archivo, con valores por defecto

        switch (tipo) {
            case "xml" -> repo = new FicheroXML(ruta);
            case "binario" -> repo = new FicheroBinario(ruta);
            default -> repo = new FicheroCSV(ruta);
        }
        // Crea la instancia de repositorio según el tipo: XML, binario o CSV
    }

    public void cargar() throws Exception {
        memoria = repo.cargar();
        // Carga la lista de músicos desde el repositorio y la guarda en memoria
    }

    public void guardar() throws Exception {
        repo.guardar(memoria);
        // Guarda la lista de músicos en el repositorio configurado
    }

    public List<Musico> listar() {
        return memoria;
        // Devuelve la lista de músicos en memoria
    }

    public void agregar(Musico m) {
        memoria.removeIf(x -> x.getIdMusico() == m.getIdMusico());
        memoria.add(m);
        // Añade un músico, reemplazando cualquier músico con el mismo ID
    }

    public Musico buscar(int id) {
        return memoria.stream().filter(m -> m.getIdMusico() == id).findFirst().orElse(null);
        // Busca un músico por su ID en la lista de memoria
        // Devuelve null si no lo encuentra
    }

    public boolean eliminar(int id) {
        return memoria.removeIf(m -> m.getIdMusico() == id);
        // Elimina un músico por su ID y devuelve true si se eliminó, false si no existía
    }

    public String getRuta() {
        return repo.getRuta();
        // Devuelve la ruta del archivo o repositorio que se está usando
    }
}
