import java.io.IOException;
import java.util.List;

public class ExamenPruebaMain {
    public static void main(String[] args) throws Exception {
        ExamenPruebaFicheroAleatorio aleatorio = new ExamenPruebaFicheroAleatorio();
        ExamenPruebaFicheroCSV csv = new ExamenPruebaFicheroCSV();
        ExamenPruebaFicheroXML xml = new ExamenPruebaFicheroXML();
        ExamenPruebaFicheroBinario binario = new ExamenPruebaFicheroBinario();

        List<ExamenPrueba> lista = xml.leerXML("data/examenes.xml");
        //xml.escribirXML(lista,"data/examenes.xml");
        //binario.escribirBinario(lista,"data/examenes.dat");
        //aleatorio.escribirAleatorio(lista,"data/examenes.raf");
        //aleatorio.escribirAleatorioAPartirID(lista,"data/examenesFiltrado.raf",4);
        //aleatorio.escribirAleatorioID(lista,"data/examenesFiltrado.raf", 3);
        //List<ExamenPrueba> lista2 = aleatorio.leerAleatorio("data/examenesFiltrado.raf");

        for(ExamenPrueba fichero : lista){
            System.out.println(fichero);
        }
    }
}
