import org.w3c.dom.*;

import javax.xml.crypto.dsig.Transform;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FicheroXML {

    public void escribirXML(List<Producto> lista, String ruta) throws Exception{

        // Crear documento vacio
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // Crear elemento raiz <productos>
        Element root = doc.createElement("productos");
        doc.appendChild(root);

        // Recorrer la lista y crear etiquetas <producto>
        for(Producto p : lista){
            Element eProducto = doc.createElement("producto");

            // <ID>
            Element eID = doc.createElement("id");
            eID.appendChild(doc.createTextNode(String.valueOf(p.getId())));
            eProducto.appendChild(eID);

            // <Nombre>
            Element eNombre = doc.createElement("nombre");
            eNombre.appendChild(doc.createTextNode(p.getNombre()));
            eProducto.appendChild(eNombre);

            // <Precio>
            Element ePrecio = doc.createElement("precio");
            ePrecio.appendChild(doc.createTextNode(String.valueOf(p.getPrecio())));
            eProducto.appendChild(ePrecio);

            root.appendChild(eProducto);
        }

        // Guarda el documento XML en un archivo
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(ruta));
        transformer.transform(source, result);
    }

    /**
     * Lee el archivo XML y devuelve una lista
     * @param ruta
     * @return
     * @throws Exception
     */
    public List<Producto> leerXML(String ruta) throws Exception{
        List<Producto> lista = new ArrayList<Producto>();

        // Comprobamos que el archivo existe
        File archivo = new File(ruta);
        if(!archivo.exists()){
            throw new IllegalArgumentException("El archivo no existe "+ruta);
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // crea una “fábrica” que construye parsers.
        DocumentBuilder builder = factory.newDocumentBuilder(); // crea un parser real (DocumentBuilder)
        Document doc = builder.parse(archivo); // lee el archivo XML y lo convierte en un Document, es decir, una representación en memoria del XML.
        doc.getDocumentElement().normalize(); // lee el archivo XML y lo convierte en un Document, es decir, una representación en memoria del XML.

        // Busca todos los nodo <producto> y los devuelve como NodeList (array de nodos)
        NodeList productos = doc.getElementsByTagName("producto");

        // Recorrer cada nodo <producto> en el NodeList
        for(int i = 0; i < productos.getLength(); i++){
            Node nodo = productos.item(i);

            // Comprobar que el nodo sea un elemento (no texto o comentario)
            if(nodo.getNodeType() == Node.ELEMENT_NODE){
                // nodo convertido a elemento
                Element e = (Element) nodo;

                // Leer atributos del nodo <producto>
                // .getElementsByTagName("id").item(0) → obtiene el primer (y único) <id> dentro del <producto>.
                // .getTextContext() → extrae el texto que hay dentro de la etiqueta, por ejemplo "1".
                int id = Integer.parseInt(e.getElementsByTagName("id").item(0).getTextContent());
                String nombre = e.getElementsByTagName("nombre").item(0).getTextContent();
                double precio = Double.parseDouble(e.getElementsByTagName("precio").item(0).getTextContent());

                lista.add(new Producto(id, nombre, precio));
            }
        }
        return lista;
    }

    public void escribirXMLAlumno(List<Alumno>lista, String ruta)throws Exception{
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("alumnos");
            doc.appendChild(root);

            for(Alumno a : lista){
                Element alumnoElement = doc.createElement("alumno");

                alumnoElement.setAttribute("id",String.valueOf(a.getIdAlumno()));

                Element nombre = doc.createElement("nombre");
                nombre.setTextContent(a.getNombreAlumno());
                alumnoElement.appendChild(nombre);

                Element nota = doc.createElement("nota");
                nota.setTextContent(String.valueOf(a.getNotaAlumno()));
                alumnoElement.appendChild(nota);

                root.appendChild(alumnoElement);
            }

            // Guarda el documento XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(ruta));
            transformer.transform(source, result);

            System.out.println("XML creado correctamente");
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public List<Alumno> leerXMLAlumno(String ruta)throws Exception{
        List<Alumno> lista = new ArrayList<>();
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(ruta));
            doc.getDocumentElement().normalize();

            NodeList nodos = doc.getElementsByTagName("alumno");

            for(int i = 0; i < nodos.getLength(); i++){
                Node nodo = nodos.item(i);
                if(nodo.getNodeType() == Node.ELEMENT_NODE){
                    Element elem = (Element) nodo;
                    int id = Integer.parseInt(elem.getAttribute("id"));
                    String nombre = elem.getElementsByTagName("nombre").item(0).getTextContent();
                    double nota = Double.parseDouble(elem.getElementsByTagName("nota").item(0).getTextContent());

                    lista.add(new Alumno(id, nombre, nota));
                }
            }
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return lista;
    }
}
