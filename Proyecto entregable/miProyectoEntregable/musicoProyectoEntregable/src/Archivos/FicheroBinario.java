package Archivos;

import Interfaces.MusicoRepositorio;
import Objetos.Musico;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FicheroBinario implements MusicoRepositorio {

    private final String ruta;

    public FicheroBinario(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public List<Musico> cargar() throws IOException, ClassNotFoundException {
        File file = new File(ruta);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return (List<Musico>) ois.readObject();
        }
    }

    @Override
    public void guardar(List<Musico> musicos) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(musicos);
        }
    }

    @Override
    public Optional<Musico> buscarPorId(int id) throws Exception {
        return cargar().stream()
                .filter(m -> m.getIdMusico() == id)
                .findFirst();
    }

    @Override
    public Musico añadir(Musico musico) throws Exception {
        List<Musico> musicos = cargar();
        if (musico.getIdMusico() == 0) {
            int maxId = musicos.stream().mapToInt(Musico::getIdMusico).max().orElse(0);
            musico.setIdMusico(maxId + 1);
        }
        musicos.add(musico);
        guardar(musicos);
        return musico;
    }

    @Override
    public boolean modificar(Musico musico) throws Exception {
        List<Musico> musicos = cargar();
        Optional<Musico> musicoExistente = musicos.stream()
                .filter(m -> m.getIdMusico() == musico.getIdMusico())
                .findFirst();

        if (musicoExistente.isPresent()) {
            musicos.remove(musicoExistente.get());
            musicos.add(musico);
            guardar(musicos);
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        List<Musico> musicos = cargar();
        boolean removed = musicos.removeIf(m -> m.getIdMusico() == id);
        if (removed) {
            guardar(musicos);
        }
        return removed;
    }

    @Override
    public String getRuta() {
        return this.ruta;
    }
}
