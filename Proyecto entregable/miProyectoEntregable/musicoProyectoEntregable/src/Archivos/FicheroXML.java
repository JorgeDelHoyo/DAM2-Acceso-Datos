package Archivos;

import Objetos.Musico;
import Objetos.Banda;
import Interfaces.MusicoRepositorio;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

public class FicheroXML implements MusicoRepositorio {

    private final Path ruta;

    public FicheroXML(String rutaArchivo) {
        this.ruta = Paths.get(rutaArchivo);
    }

    @Override
    public List<Musico> cargar() throws Exception {
        List<Musico> lista = new ArrayList<>();
        if (!Files.exists(ruta)) return lista;

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

    @Override
    public void guardar(List<Musico> lista) throws Exception {
        Files.createDirectories(ruta.getParent() == null ? Paths.get(".") : ruta.getParent());

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
    public String getRuta() {
        return ruta.toString();
    }
}
