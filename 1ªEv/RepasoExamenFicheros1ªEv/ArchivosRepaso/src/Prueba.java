import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Prueba {
    public List<ExamenPrueba> leerFicheroCSV(String ruta)throws IOException {
        List<ExamenPrueba> lista = new ArrayList<>();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(ruta));
            String linea;
            while((linea = br.readLine())!= null){
                String[]partesLista = linea.split(",");
                int id = Integer.parseInt(partesLista[0]);
                String nombre = partesLista[1];
                float notaMaxima = Float.parseFloat(partesLista[2]);
                String fecha = partesLista[3];
                boolean aprobado = Boolean.parseBoolean(partesLista[4]);

                lista.add(new ExamenPrueba(id,nombre,notaMaxima,fecha,aprobado));
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }finally {
            if(br!=null){
                br.close();
            }
        }
        return lista;
    }

    public void escribirFicheroCSV(List<ExamenPrueba> lista, String ruta)throws IOException {
        BufferedWriter bw = null;
        try{
            bw = new BufferedWriter(new FileWriter(ruta));
            for(ExamenPrueba e: lista){
                bw.write(e.getId()+","+e.getNombre()+","+e.getNotaMaxima()+","+e.getFecha()+","+e.isAprobado());
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            if(bw!=null){
                bw.close();
            }
        }
    }

    public List<ExamenPrueba> leerFicheroBinario(String ruta)throws IOException {
        ObjectInputStream ois = null;
        List<ExamenPrueba> lista = new ArrayList<>();
        try{
            ois = new ObjectInputStream(new FileInputStream(ruta));
            while(true){
                try{
                    ExamenPrueba e = (ExamenPrueba) ois.readObject();
                    lista.add(e);
                }catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }catch (EOFException e){
                    break;
                }
            }
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }finally {
            if(ois!=null){
                ois.close();
            }
        }
        return lista;
    }

    public void escribirFicheroBinario(List<ExamenPrueba> lista, String ruta)throws IOException {
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(new FileOutputStream(ruta));
            oos.writeObject(lista);
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }finally {
            if(oos!=null){
                oos.close();
            }
        }
    }

    public List<ExamenPrueba> leerFicheroXML(String ruta)throws Exception {
        List<ExamenPrueba> lista = new ArrayList<>();
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(ruta));
            doc.getDocumentElement().normalize();

            NodeList nodos = doc.getElementsByTagName("examen");

            for(int i = 0; i < nodos.getLength(); i++){
                Node nodo = nodos.item(i);
                if(nodo.getNodeType() == Node.ELEMENT_NODE){
                    Element e = (Element) nodo;
                    int id = Integer.parseInt(e.getAttribute("id"));
                    String nombre = e.getElementsByTagName("nombre").item(0).getTextContent();
                    float notaMaxima = Float.parseFloat(e.getElementsByTagName("notaMaxima").item(0).getTextContent());
                    String fecha = e.getElementsByTagName("fecha").item(0).getTextContent();
                    boolean aprobado = Boolean.parseBoolean(e.getElementsByTagName("aprobado").item(0).getTextContent());

                    lista.add(new ExamenPrueba(id,nombre,notaMaxima,fecha,aprobado));
                }
            }
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public void escribirXML(List<ExamenPrueba> lista, String ruta)throws Exception {
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("examenes");
            doc.appendChild(root);

            for(ExamenPrueba e: lista){
                Element examenElement = doc.createElement("examen");
                examenElement.setAttribute("id",String.valueOf(e.getId()));

                Element nombreElement = doc.createElement("nombre");
                nombreElement.setTextContent(e.getNombre());
                examenElement.appendChild(nombreElement);

                Element notaMaximaElement = doc.createElement("notaMaxima");
                notaMaximaElement.setTextContent(String.valueOf(e.getNotaMaxima()));
                examenElement.appendChild(notaMaximaElement);

                Element fechaElement = doc.createElement("fecha");
                fechaElement.setTextContent(e.getFecha());
                examenElement.appendChild(fechaElement);

                Element aprobadoElement = doc.createElement("aprobado");
                aprobadoElement.setTextContent(String.valueOf(e.isAprobado()));
                examenElement.appendChild(aprobadoElement);

                root.appendChild(examenElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new StringWriter());
            transformer.transform(source, result);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
