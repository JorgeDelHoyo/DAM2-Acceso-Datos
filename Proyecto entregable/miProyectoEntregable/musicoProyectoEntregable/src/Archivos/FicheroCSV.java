package Archivos;

import Interfaces.MusicoRepositorio;
import Objetos.Musico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FicheroCSV implements MusicoRepositorio {

    private final String ruta;
    private static final String SEPARATOR = ",";

    public FicheroCSV(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public List<Musico> cargar() throws IOException {
        List<Musico> musicos = new ArrayList<>();
        File file = new File(ruta);
        if (!file.exists()) {
            // Si el archivo no existe, devuelve una lista vacía.
            return musicos;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(SEPARATOR);
                if (values.length >= 3) {
                    musicos.add(new Musico(Integer.parseInt(values[0]), values[1], values[2]));
                }
            }
        }
        return musicos;
    }

    @Override
    public void guardar(List<Musico> musicos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            for (Musico musico : musicos) {
                String line = musico.getIdMusico() + SEPARATOR + musico.getNombre() + SEPARATOR + musico.getInstrumento();
                bw.write(line);
                bw.newLine();
            }
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
        // Asignar un nuevo ID si es necesario (simulando autoincremento)
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
