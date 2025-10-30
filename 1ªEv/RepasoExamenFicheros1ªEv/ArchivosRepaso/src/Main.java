
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        FicheroCSV csv = new FicheroCSV();
        FicheroXML xml = new FicheroXML();
        FicheroBinario bin = new FicheroBinario();
        FicheroAleatorio rafFile = new FicheroAleatorio();


        //List<Producto> lista = xml.leerXML("productos.xml");
        //lista.forEach(System.out::println);

        //csv.escribirCSV(lista,"productos.csv");
        //xml.escribirXML(lista, "productos.xml");
        //bin.escribirBinario(lista, "productos.dat");
        //rafFile.escribirAleatorio(lista, "productos.raf");

        List<Producto> lista = bin.leerBinario("productos2.dat");
        bin.escribirBinario(lista,"productos2.dat");
        //bin.escribirBinarioAlumno(listaAlumnos,"alumnosEscrito.dat");
        //csv.escribirCSVAlumno(listaAlumnos, "alumnos2.csv");
        //bin.escribirBinarioAlumno(listaAlumnos, "alumnos.dat");
        //rafFile.escribirAleatorioAlumno(listaAlumnos, "alumnos.raf");


        //List<Alumno> alumnos = rafFile.leerAleatorioAlumnosAPartirID("alumnos.raf",1);
        //rafFile.escribirAleatorioAlumnoID(alumnos,"alumnos_2.raf",3);
        //rafFile.escribirAleatorioPorID(alumnos,"alumnos_2.raf",2);
        //List<Alumno> alumnos2 = rafFile.leerAleatorioAlumno("alumnos_2.raf");
        for(Producto p : lista){
            System.out.println(p);
        }
    }
}
