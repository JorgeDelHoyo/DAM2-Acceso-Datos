package Archivos;

import Objetos.Musico;
import Objetos.Banda;
import Interfaces.MusicoRepositorio;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Clase FicheroCSV
 * Implementa la interfaz MusicoRepositorio para guardar y cargar músicos en formato CSV.
 * Cada músico puede tener varias bandas, separadas por "|" en el archivo.
 */
public class FicheroCSV implements MusicoRepositorio {

    // Ruta del archivo CSV donde se guardarán o cargarán los músicos
    private final Path ruta;

    /**
     * Constructor que recibe la ruta del archivo CSV
     * @param rutaArchivo ruta del archivo CSV
     */
    public FicheroCSV(String rutaArchivo) {
        this.ruta = Paths.get(rutaArchivo);
    }

    /**
     * Metodo para cargar la lista de músicos desde un archivo CSV
     * @return lista de músicos con sus bandas
     * @throws IOException si ocurre un error al leer el archivo
     */
    @Override
    public List<Musico> cargar() throws IOException {
        List<Musico> lista = new ArrayList<>();

        // Si el archivo no existe, devuelve lista vacía
        if (!Files.exists(ruta)) return lista;

        // Abrir el archivo para lectura
        try (BufferedReader br = Files.newBufferedReader(ruta)) {
            String line;
            while ((line = br.readLine()) != null) {
                // Cada línea tiene el formato:
                // idMusico,nombre,instrumento,banda1|banda2|...

                // Separa la línea en máximo 4 partes
                String[] partes = line.split(",", 4);

                // Crear objeto Musico con id, nombre e instrumento
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                String instrumento = partes[2];

                Musico m = new Musico(id, nombre, instrumento);

                // Si hay bandas, se separan por "|"
                if (partes.length == 4 && !partes[3].isEmpty()) {
                    String[] bandas = partes[3].split("\\|");
                    for (String b : bandas) {
                        m.getBandas().add(new Banda(0, b)); // idBanda desconocido en CSV
                    }
                }
                lista.add(m); // Añadir el músico a la lista
            }
        }
        return lista; // Devuelve la lista completa de músicos
    }

    /**
     * Metodo para guardar la lista de músicos en un archivo CSV
     * @param lista lista de músicos con sus bandas
     * @throws IOException si ocurre un error al escribir el archivo
     */
    @Override
    public void guardar(List<Musico> lista) throws IOException {
        // Crear directorios si no existen
        Files.createDirectories(ruta.getParent() == null ? Paths.get(".") : ruta.getParent());

        // Abrir el archivo para escritura
        try (BufferedWriter bw = Files.newBufferedWriter(ruta)) {
            for (Musico m : lista) {
                StringBuilder sb = new StringBuilder();

                // Agregar id, nombre e instrumento separados por coma
                sb.append(m.getIdMusico()).append(",");
                sb.append(m.getNombre()).append(",");
                sb.append(m.getInstrumento()).append(",");

                // Agregar las bandas separadas por "|"
                for (int i = 0; i < m.getBandas().size(); i++) {
                    sb.append(m.getBandas().get(i).getNombreBanda());
                    if (i < m.getBandas().size() - 1) sb.append("|");
                }

                // Escribir la línea en el archivo
                bw.write(sb.toString());
                bw.newLine();
            }
        }
    }

    /**
     * Devuelve la ruta del archivo CSV
     * @return ruta como String
     */
    @Override
    public String getRuta() {
        return ruta.toString();
    }
}
