import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExamenPruebaFicheroXML {

    public List<ExamenPrueba> leerXML (String ruta) throws Exception{
        List<ExamenPrueba> lista = new ArrayList<>();
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(ruta));
            doc.getDocumentElement().normalize();

            NodeList nodos = doc.getElementsByTagName("examen");

            for(int i = 0; i < nodos.getLength(); i++){
                Node nodo = nodos.item(i);
                if(nodo.getNodeType() == Node.ELEMENT_NODE){
                    Element elem = (Element) nodo;
                    int id = Integer.parseInt(elem.getAttribute("id"));
                    String nombre = elem.getElementsByTagName("nombre").item(0).getTextContent();
                    float notaMaxima = Float.parseFloat(elem.getElementsByTagName("notaMaxima").item(0).getTextContent());
                    String fecha = elem.getElementsByTagName("fecha").item(0).getTextContent();
                    boolean aprobado =  Boolean.parseBoolean(elem.getElementsByTagName("aprobado").item(0).getTextContent());

                    lista.add(new ExamenPrueba(id,nombre,notaMaxima,fecha,aprobado));
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public void escribirXML(List<ExamenPrueba>lista, String ruta)throws Exception{
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("examenes");
            document.appendChild(root);

            for(ExamenPrueba e : lista){
                Element examenElement = document.createElement("examen");
                examenElement.setAttribute("id",String.valueOf(e.getId()));

                Element nombreElement = document.createElement("nombre");
                nombreElement.setTextContent(e.getNombre());
                examenElement.appendChild(nombreElement);

                Element notaMaximaElement = document.createElement("notaMaxima");
                notaMaximaElement.setTextContent(String.valueOf(e.getNotaMaxima()));
                examenElement.appendChild(notaMaximaElement);

                Element fechaElement = document.createElement("fecha");
                fechaElement.setTextContent(String.valueOf(e.getFecha()));
                examenElement.appendChild(fechaElement);

                Element aprobadoElement = document.createElement("aprobado");
                aprobadoElement.setTextContent(String.valueOf(e.isAprobado()));
                examenElement.appendChild(aprobadoElement);

                root.appendChild(examenElement);

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(ruta));
            transformer.transform(source, result);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
