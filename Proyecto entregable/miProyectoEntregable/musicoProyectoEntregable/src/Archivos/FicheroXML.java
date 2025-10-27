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
/**
 * Clase FicheroXML
 * Implementa la interfaz MusicoRepositorio para guardar y cargar músicos en formato XML.
 * Cada músico puede tener varias bandas asociadas.
 */
public class FicheroXML implements MusicoRepositorio {

    // Ruta del archivo XML donde se guardarán o cargarán los músicos
    private final Path ruta;

    /**
     * Constructor que recibe la ruta del archivo XML
     * @param rutaArchivo ruta del archivo XML
     */
    public FicheroXML(String rutaArchivo) {
        this.ruta = Paths.get(rutaArchivo);
    }

    /**
     * Metodo para cargar la lista de músicos desde un archivo XML
     * @return lista de músicos con sus bandas
     * @throws Exception si ocurre un error al leer o parsear el archivo XML
     */
    @Override
    public List<Musico> cargar() throws Exception {
        List<Musico> lista = new ArrayList<>();

        // Si el archivo no existe, devuelve lista vacía
        if (!Files.exists(ruta)) return lista;

        // Crear parser de XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        // Parsear el archivo XML a un Document
        Document doc = db.parse(Files.newInputStream(ruta));

        // Obtener todos los elementos <Musico> del XML
        NodeList musicos = doc.getElementsByTagName("musico");
        for (int i = 0; i < musicos.getLength(); i++) {
            Element elem = (Element) musicos.item(i);

            // Leer atributos y elementos del músico
            int id = Integer.parseInt(elem.getAttribute("id"));
            String nombre = elem.getElementsByTagName("nombre").item(0).getTextContent();
            String instrumento = elem.getElementsByTagName("instrumento").item(0).getTextContent();

            // Crear objeto Musico
            Musico m = new Musico(id, nombre, instrumento);

            // Leer todas las bandas asociadas al músico
            NodeList bandas = elem.getElementsByTagName("banda");
            for (int j = 0; j < bandas.getLength(); j++) {
                Element bElem = (Element) bandas.item(j);
                int idBanda = Integer.parseInt(bElem.getAttribute("id"));
                String nombreBanda = bElem.getTextContent();

                // Añadir banda al músico
                m.getBandas().add(new Banda(idBanda, nombreBanda));
            }
            lista.add(m); // Añadir músico a la lista
        }
        return lista;
    }

    /**
     * Metodo para guardar la lista de músicos en un archivo XML
     * @param lista lista de músicos con sus bandas
     * @throws Exception si ocurre un error al escribir el archivo XML
     */
    @Override
    public void guardar(List<Musico> lista) throws Exception {
        // Crear carpetas si no existen
        Files.createDirectories(ruta.getParent() == null ? Paths.get(".") : ruta.getParent());

        // Crear documento XML en memoria
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        // Crear elemento raíz "musicos"
        Element root = doc.createElement("musicos");
        doc.appendChild(root);

        // Recorrer cada músico de la lista
        for (Musico m : lista) {
            // Crear elemento "musico" con atributo id
            Element musicoElem = doc.createElement("musico");
            musicoElem.setAttribute("id", String.valueOf(m.getIdMusico()));

            // Crear y añadir elemento "nombre"
            Element nombreElem = doc.createElement("nombre");
            nombreElem.setTextContent(m.getNombre());
            musicoElem.appendChild(nombreElem);

            // Crear y añadir elemento "instrumento"
            Element instrumentoElem = doc.createElement("instrumento");
            instrumentoElem.setTextContent(m.getInstrumento());
            musicoElem.appendChild(instrumentoElem);

            // Crear y añadir elementos "banda" para cada banda del músico
            for (Banda b : m.getBandas()) {
                Element bandaElem = doc.createElement("banda");
                bandaElem.setAttribute("id", String.valueOf(b.getIdBanda()));
                bandaElem.setTextContent(b.getNombreBanda());
                musicoElem.appendChild(bandaElem);
            }

            // Añadir el músico al elemento raíz
            root.appendChild(musicoElem);
        }

        // Transformar el Document a XML en el archivo
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(doc), new StreamResult(Files.newOutputStream(ruta)));
    }

    /**
     * Devuelve la ruta del archivo XML
     * @return ruta como String
     */
    @Override
    public String getRuta() {
        return ruta.toString();
    }
}
