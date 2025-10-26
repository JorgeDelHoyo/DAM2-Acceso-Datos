package Archivos;

import Objetos.Musico;
import Objetos.Banda;
import Interfaces.MusicoRepositorio;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FicheroCSV implements MusicoRepositorio {

    private final Path ruta;

    public FicheroCSV(String rutaArchivo) {
        this.ruta = Paths.get(rutaArchivo);
    }

    @Override
    public List<Musico> cargar() throws IOException {
        List<Musico> lista = new ArrayList<>();
        if (!Files.exists(ruta)) return lista;

        try (BufferedReader br = Files.newBufferedReader(ruta)) {
            String line;
            while ((line = br.readLine()) != null) {
                // Formato: idMusico,nombre,instrumento,banda1|banda2|...
                String[] partes = line.split(",", 4);
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                String instrumento = partes[2];

                Musico m = new Musico(id, nombre, instrumento);

                if (partes.length == 4 && !partes[3].isEmpty()) {
                    String[] bandas = partes[3].split("\\|");
                    for (String b : bandas) {
                        m.getBandas().add(new Banda(0, b)); // idBanda desconocido en CSV
                    }
                }
                lista.add(m);
            }
        }

        return lista;
    }

    @Override
    public void guardar(List<Musico> lista) throws IOException {
        Files.createDirectories(ruta.getParent() == null ? Paths.get(".") : ruta.getParent());

        try (BufferedWriter bw = Files.newBufferedWriter(ruta)) {
            for (Musico m : lista) {
                StringBuilder sb = new StringBuilder();
                sb.append(m.getIdMusico()).append(",");
                sb.append(m.getNombre()).append(",");
                sb.append(m.getInstrumento()).append(",");
                for (int i = 0; i < m.getBandas().size(); i++) {
                    sb.append(m.getBandas().get(i).getNombreBanda());
                    if (i < m.getBandas().size() - 1) sb.append("|");
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        }
    }

    @Override
    public String getRuta() {
        return ruta.toString();
    }
}
