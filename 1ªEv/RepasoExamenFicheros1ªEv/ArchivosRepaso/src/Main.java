
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        FicheroCSV csv = new FicheroCSV();
        FicheroXML xml = new FicheroXML();
        FicheroBinario bin = new FicheroBinario();
        FicheroAleatorio rafFile = new FicheroAleatorio();

        List<Producto> lista = xml.leerXML("C:\\Users\\Usuario\\Desktop\\DAM\\25-26\\AccesoDatos\\DAM2-Acceso-Datos\\1ªEv\\RepasoExamenFicheros1ªEv\\ArchivosRepaso\\productos.xml");
        lista.forEach(System.out::println);

        csv.escribirCSV(lista,"productos.csv");
        //xml.escribirXML(lista, "productos.xml");
        bin.escribirBinario(lista, "productos.dat");
        rafFile.escribirAleatorio(lista, "productos.raf");

    }
}
