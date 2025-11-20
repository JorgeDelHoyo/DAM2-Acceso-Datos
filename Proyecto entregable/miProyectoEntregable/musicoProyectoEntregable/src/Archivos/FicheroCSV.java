package Archivos;

import Interfaces.MusicoRepositorio;
import Objetos.Banda;
import Objetos.Musico;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FicheroCSV implements MusicoRepositorio {

    private final Path ruta;

    public FicheroCSV(String rutaArchivo) {
        this.ruta = Paths.get(rutaArchivo);
    }

    private List<Musico> leerDelFichero() throws IOException {
        if (!Files.exists(ruta)) {
            return new ArrayList<>();
        }
        List<Musico> lista = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(ruta)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] partes = line.split(",", 4);
                if (partes.length < 3) continue;

                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                String instrumento = partes[2];
                Musico m = new Musico(id, nombre, instrumento);

                if (partes.length == 4 && !partes[3].isEmpty()) {
                    String[] bandas = partes[3].split("\\|");
                    for (String b : bandas) {
                        // NOTA: El formato CSV no almacena IDs de banda, por lo que se asigna 0.
                        m.getBandas().add(new Banda(0, b));
                    }
                }
                lista.add(m);
            }
        }
        return lista;
    }

    private void escribirEnFichero(List<Musico> lista) throws IOException {
        Files.createDirectories(ruta.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(ruta)) {
            for (Musico m : lista) {
                StringBuilder sb = new StringBuilder();
                sb.append(m.getIdMusico()).append(",");
                sb.append(m.getNombre()).append(",");
                sb.append(m.getInstrumento()).append(",");

                String bandasStr = m.getBandas().stream()
                        .map(Banda::getNombreBanda)
                        .collect(Collectors.joining("|"));
                sb.append(bandasStr);

                bw.write(sb.toString());
                bw.newLine();
            }
        }
    }

    @Override
    public List<Musico> obtenerTodos() throws SQLException {
        try {
            return leerDelFichero();
        } catch (IOException e) {
            throw new SQLException("Error de E/S al leer el fichero CSV.", e);
        }
    }

    @Override
    public Optional<Musico> obtenerPorId(int id) throws SQLException {
        return obtenerTodos().stream().filter(m -> m.getIdMusico() == id).findFirst();
    }

    @Override
    public void guardar(Musico musico) throws SQLException {
        try {
            List<Musico> musicos = leerDelFichero();
            int maxId = musicos.stream().mapToInt(Musico::getIdMusico).max().orElse(0);
            musico.setIdMusico(maxId + 1);
            musicos.add(musico);
            escribirEnFichero(musicos);
        } catch (IOException e) {
            throw new SQLException("Error de E/S al guardar en el fichero CSV.", e);
        }
    }

    @Override
    public void actualizar(Musico musico) throws SQLException {
        try {
            List<Musico> musicos = leerDelFichero();
            musicos.removeIf(m -> m.getIdMusico() == musico.getIdMusico());
            musicos.add(musico);
            escribirEnFichero(musicos);
        } catch (IOException e) {
            throw new SQLException("Error de E/S al actualizar el fichero CSV.", e);
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        try {
            List<Musico> musicos = leerDelFichero();
            musicos.removeIf(m -> m.getIdMusico() == id);
            escribirEnFichero(musicos);
        } catch (IOException e) {
            throw new SQLException("Error de E/S al eliminar del fichero CSV.", e);
        }
    }

    @Override
    public List<Musico> buscarPorBanda(int idBanda) throws SQLException {
        // ADVERTENCIA: Esta implementación es limitada. El formato CSV no guarda IDs de banda,
        // por lo que este método solo funcionará si el ID de banda que se busca es 0.
        return obtenerTodos().stream()
                .filter(m -> m.getBandas().stream().anyMatch(b -> b.getIdBanda() == idBanda))
                .collect(Collectors.toList());
    }

    public String getRuta() {
        return ruta.toString();
    }
}
