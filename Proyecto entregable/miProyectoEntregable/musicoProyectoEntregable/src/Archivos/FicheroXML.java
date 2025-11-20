package Archivos;

import Interfaces.MusicoRepositorio;
import Objetos.Banda;
import Objetos.Musico;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FicheroXML implements MusicoRepositorio {

    private final Path ruta;

    public FicheroXML(String rutaArchivo) {
        this.ruta = Paths.get(rutaArchivo);
    }

    private List<Musico> leerDelFichero() throws Exception {
        if (!Files.exists(ruta)) {
            return new ArrayList<>();
        }
        List<Musico> lista = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(Files.newInputStream(ruta));

        NodeList musicos = doc.getElementsByTagName("musico");
        for (int i = 0; i < musicos.getLength(); i++) {
            Element elem = (Element) musicos.item(i);
            int id = Integer.parseInt(elem.getAttribute("id"));
            String nombre = elem.getElementsByTagName("nombre").item(0).getTextContent();
            String instrumento = elem.getElementsByTagName("instrumento").item(0).getTextContent();
            Musico m = new Musico(id, nombre, instrumento);

            NodeList bandas = elem.getElementsByTagName("banda");
            for (int j = 0; j < bandas.getLength(); j++) {
                Element bElem = (Element) bandas.item(j);
                int idBanda = Integer.parseInt(bElem.getAttribute("id"));
                String nombreBanda = bElem.getTextContent();
                m.getBandas().add(new Banda(idBanda, nombreBanda));
            }
            lista.add(m);
        }
        return lista;
    }

    private void escribirEnFichero(List<Musico> lista) throws Exception {
        Files.createDirectories(ruta.getParent());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        Element root = doc.createElement("musicos");
        doc.appendChild(root);

        for (Musico m : lista) {
            Element musicoElem = doc.createElement("musico");
            musicoElem.setAttribute("id", String.valueOf(m.getIdMusico()));
            Element nombreElem = doc.createElement("nombre");
            nombreElem.setTextContent(m.getNombre());
            musicoElem.appendChild(nombreElem);
            Element instrumentoElem = doc.createElement("instrumento");
            instrumentoElem.setTextContent(m.getInstrumento());
            musicoElem.appendChild(instrumentoElem);

            for (Banda b : m.getBandas()) {
                Element bandaElem = doc.createElement("banda");
                bandaElem.setAttribute("id", String.valueOf(b.getIdBanda()));
                bandaElem.setTextContent(b.getNombreBanda());
                musicoElem.appendChild(bandaElem);
            }
            root.appendChild(musicoElem);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(doc), new StreamResult(Files.newOutputStream(ruta)));
    }

    @Override
    public List<Musico> obtenerTodos() throws SQLException {
        try {
            return leerDelFichero();
        } catch (Exception e) {
            throw new SQLException("Error de E/S o parseo al leer el fichero XML.", e);
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
        } catch (Exception e) {
            throw new SQLException("Error de E/S o parseo al guardar en el fichero XML.", e);
        }
    }

    @Override
    public void actualizar(Musico musico) throws SQLException {
        try {
            List<Musico> musicos = leerDelFichero();
            musicos.removeIf(m -> m.getIdMusico() == musico.getIdMusico());
            musicos.add(musico);
            escribirEnFichero(musicos);
        } catch (Exception e) {
            throw new SQLException("Error de E/S o parseo al actualizar el fichero XML.", e);
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        try {
            List<Musico> musicos = leerDelFichero();
            musicos.removeIf(m -> m.getIdMusico() == id);
            escribirEnFichero(musicos);
        } catch (Exception e) {
            throw new SQLException("Error de E/S o parseo al eliminar del fichero XML.", e);
        }
    }

    @Override
    public List<Musico> buscarPorBanda(int idBanda) throws SQLException {
        return obtenerTodos().stream()
                .filter(m -> m.getBandas().stream().anyMatch(b -> b.getIdBanda() == idBanda))
                .collect(Collectors.toList());
    }
}
