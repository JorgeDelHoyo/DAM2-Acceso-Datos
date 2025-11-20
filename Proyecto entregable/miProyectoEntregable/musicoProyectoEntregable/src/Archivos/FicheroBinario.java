package Archivos;

import Interfaces.MusicoRepositorio;
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

public class FicheroBinario implements MusicoRepositorio {

    private final Path ruta;

    public FicheroBinario(String rutaArchivo) {
        this.ruta = Paths.get(rutaArchivo);
    }

    private List<Musico> leerDelFichero() throws IOException, ClassNotFoundException {
        if (!Files.exists(ruta)) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(ruta))) {
            return (List<Musico>) ois.readObject();
        }
    }

    private void escribirEnFichero(List<Musico> musicos) throws IOException {
        Files.createDirectories(ruta.getParent());
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(ruta))) {
            oos.writeObject(musicos);
        }
    }

    @Override
    public List<Musico> obtenerTodos() throws SQLException {
        try {
            return leerDelFichero();
        } catch (IOException | ClassNotFoundException e) {
            throw new SQLException("Error de E/S al leer el fichero binario.", e);
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
        } catch (IOException | ClassNotFoundException e) {
            throw new SQLException("Error de E/S al guardar en el fichero binario.", e);
        }
    }

    @Override
    public void actualizar(Musico musico) throws SQLException {
        try {
            List<Musico> musicos = leerDelFichero();
            musicos.removeIf(m -> m.getIdMusico() == musico.getIdMusico());
            musicos.add(musico);
            escribirEnFichero(musicos);
        } catch (IOException | ClassNotFoundException e) {
            throw new SQLException("Error de E/S al actualizar el fichero binario.", e);
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        try {
            List<Musico> musicos = leerDelFichero();
            musicos.removeIf(m -> m.getIdMusico() == id);
            escribirEnFichero(musicos);
        } catch (IOException | ClassNotFoundException e) {
            throw new SQLException("Error de E/S al eliminar del fichero binario.", e);
        }
    }

    @Override
    public List<Musico> buscarPorBanda(int idBanda) throws SQLException {
        return obtenerTodos().stream()
                .filter(m -> m.getBandas().stream().anyMatch(b -> b.getIdBanda() == idBanda))
                .collect(Collectors.toList());
    }
}
