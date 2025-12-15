package Archivos;

import Interfaces.MusicoRepositorio;
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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FicheroXML implements MusicoRepositorio {

    private final String ruta;

    public FicheroXML(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public List<Musico> cargar() throws Exception {
        List<Musico> musicos = new ArrayList<>();
        File file = new File(ruta);
        if (!file.exists()) {
            return musicos;
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("musico");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            int id = Integer.parseInt(element.getAttribute("id"));
            String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
            String instrumento = element.getElementsByTagName("instrumento").item(0).getTextContent();
            musicos.add(new Musico(id, nombre, instrumento));
        }
        return musicos;
    }

    @Override
    public void guardar(List<Musico> musicos) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        Element root = doc.createElement("musicos");
        doc.appendChild(root);

        for (Musico musico : musicos) {
            Element musicoElem = doc.createElement("musico");
            musicoElem.setAttribute("id", String.valueOf(musico.getIdMusico()));

            Element nombre = doc.createElement("nombre");
            nombre.setTextContent(musico.getNombre());
            musicoElem.appendChild(nombre);

            Element instrumento = doc.createElement("instrumento");
            instrumento.setTextContent(musico.getInstrumento());
            musicoElem.appendChild(instrumento);

            root.appendChild(musicoElem);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(ruta));
        transformer.transform(source, result);
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
