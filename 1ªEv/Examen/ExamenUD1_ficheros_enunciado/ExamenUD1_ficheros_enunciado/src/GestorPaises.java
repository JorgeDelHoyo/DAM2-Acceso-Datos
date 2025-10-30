import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class GestorPaises {

    // Colección que contiene todos los paises
    //private List<Pais> paises = new ArrayList<>();

    // Mostrar todos los países
    public void mostrarPaises(List<Pais>lista) {
        for (Pais p : lista) {
            System.out.println(p);
        }
    }

    /*
        Lee el contenido del archivo CSV
        Salta la primera linea que indica la descripción de cada campo
        Por cada linea crea un pais y añadelo al ArrayList de paises
        Garantiza el cierre de los recursos
        Captura y/o propaga la excepcion
     */
    // Ejercicio 1: leer CSV

    //Metodo para leer el CSV
    public List<Pais> leerCSV(String nombreArchivoCSV)throws IOException {
        List<Pais> paises = new ArrayList<>();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(nombreArchivoCSV));
            br.readLine(); // CABECERA
            String linea;
            while((linea = br.readLine()) != null){
                String[]partesLinea = linea.split(";");
                int id = Integer.parseInt(partesLinea[0]);
                String nombre = partesLinea[1];
                double indiceDesarrollo = Double.parseDouble(partesLinea[2]);

                paises.add(new Pais(id, nombre, indiceDesarrollo));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            if(br != null){
                br.close();
            }
        }

        return paises;
    }

    // Metodo para leer el binario
    public List<Pais> leerDAT(String nombreArchivoDAT)throws IOException {
        List<Pais> paises = new ArrayList<>();
        DataInputStream dais = null;
        try{
            dais = new DataInputStream(new FileInputStream(nombreArchivoDAT));
            int id = dais.readInt();
            char[]nombreSeparado = new char[5];
            for(int i = 0 ; i < nombreSeparado.length; i++){
                nombreSeparado[i] = dais.readChar();
            }
            String nombre = new String(nombreSeparado);
            double indiceDesarrollo = dais.readDouble();

            while(id < 10) {
                id = dais.readInt();
                char[]nombreSeparado1 = new char[5];
                for(int i = 0 ; i < nombreSeparado1.length; i++){
                    nombreSeparado1[i] = dais.readChar();
                }
                nombre = new String(nombreSeparado1);
                indiceDesarrollo = dais.readDouble();
                paises.add(new Pais(id, nombre, indiceDesarrollo));
            }
        }catch (Exception e){
            System.out.println("2"+e.getMessage());
        }finally {
            if(dais != null){
                dais.close();
            }
        }
        return paises;
    }

    /*
        Define claramente el tamaño del registro de cada Pais. Ten en cuenta que el nombre del pais siempre será de 5 letras.
        Lee el registro correspondiente al idbuscado, no es necesario contemplar la posibilidad de que el registro no exista
        Inserta el pais buscado en el arraylist
        Garantiza el cierre de los recursos
        Captura y/o propaga la excepcion
     */
    // Ejercicio 2: Leer aleatorio

    private final int REGISTRO = 4+(5*2)+8; //Int+CHAR*2*DOUBLE

    public void leerAleatorio(String nombreArchivoCSVAleatorio, int idBuscado)throws IOException {
        RandomAccessFile raf = null;
        try{
            raf = new RandomAccessFile(nombreArchivoCSVAleatorio, "rw");
            long posicionPais = (long) (idBuscado)*REGISTRO;
            raf.seek(posicionPais);
            int id = raf.readInt();
            char[]nombrePartes = new char[5];
            for(int i = 0; i < nombrePartes.length; i++){
                nombrePartes[i] = raf.readChar();
            }
            String nombre = new String(nombrePartes);
            double indiceDesarrollo = Double.parseDouble(String.valueOf(raf.readDouble()));

            Pais p = new Pais(id, nombre, indiceDesarrollo);

            System.out.println(p);
        }catch (IOException e){
            System.out.println("1"+e.getMessage());
        }finally {
            if(raf != null){
                raf.close();
            }
        }
    }

    // Ejercicio 3: Escribir XML
    /*
    <paises>
        <pais>
            <id>6</id>
            <nombre>Yemen</nombre>
            <indiceDesarrollo>0.480</indiceDesarrollo>
        </pais>
        ...
    </paises>

    Crea un archivo XML recibido y con la estructura de ejemplo
    Crear el nodo raíz <paises> y, dentro, un nodo <pais> por cada elemento del ArrayList.
    Crea el XML correctamente indentado (legible).
    Garantiza el cierre de los recursos
    Captura y/o propaga la excepcion
     */
    public void escribeXML(List<Pais> paises,String nombreArchivoXML)throws Exception {
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("paises");
            document.appendChild(root);

            for(Pais p : paises){
                Element pais = document.createElement("pais");

                Element id = document.createElement("id");
                id.appendChild(document.createTextNode(String.valueOf(p.getId())));
                pais.appendChild(id);

                Element nombre = document.createElement("nombre");
                nombre.appendChild(document.createTextNode(String.valueOf(p.getNombre())));
                pais.appendChild(nombre);

                Element indiceDesarrollo = document.createElement("indiceDesarrollo");
                indiceDesarrollo.appendChild(document.createTextNode(String.valueOf(p.getIndiceDesarrollo())));
                pais.appendChild(indiceDesarrollo);

                root.appendChild(pais);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(nombreArchivoXML));
            transformer.transform(source, result);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
