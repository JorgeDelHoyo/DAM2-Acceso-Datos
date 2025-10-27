package Archivos;

import Objetos.Musico;
import Interfaces.MusicoRepositorio;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Clase FicheroBinario
 * Implementa la interfaz MusicoRepositorio para guardar y cargar músicos en formato binario.
 * Cada músico puede tener varias bandas
 */
public class FicheroBinario implements MusicoRepositorio {

    // Ruta del archivo binario donde se guardarán o cargarán los musicos
    private final Path ruta;

    /**
     * Constructor que recibe la ruta del archivo y la convierte a Path
     * @param rutaArchivo
     */
    public FicheroBinario(String rutaArchivo) {
        this.ruta = Paths.get(rutaArchivo);
    }

    @Override
    public List<Musico> cargar() throws IOException, ClassNotFoundException {
        // Si el archivo no existe, devuelve una lista vacía
        if (!Files.exists(ruta)) return new ArrayList<>();

        /**
         * Lee la lista completa de musicos desde el archivo binario
         * Incluye todas las bandas porque Musico y Banda implementan Serializable
         */
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(ruta))) {
            return (List<Musico>) ois.readObject();
        }
    }

    @Override
    public void guardar(List<Musico> musicos) throws IOException {
        // Asegura que la carpeta del archivo exista
        Files.createDirectories(ruta.getParent() == null ? Paths.get(".") : ruta.getParent());

        /**
         * Escribe toda la lista de musicos en un archivo binario de forma serializada
         * se encarga de guardar la estructura completa de cada Musico y sus bandas
         */
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(ruta))) {
            oos.writeObject(musicos);
        }
    }

    /**
     * Devuelve la ruta del archivo binario
     * @return
     */
    @Override
    public String getRuta() { return ruta.toString(); }

}