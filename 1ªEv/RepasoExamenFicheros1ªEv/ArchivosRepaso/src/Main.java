
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

        //List<Alumno> listaAlumnos = csv.leerCSVAlumno("Alumnos.csv");
        //csv.escribirCSVAlumno(listaAlumnos, "alumnos2.csv");
       // bin.escribirBinarioAlumno(listaAlumnos, "alumnos.dat");
        //rafFile.escribirAleatorioAlumno(listaAlumnos, "alumnos.raf");


        List<Alumno> alumnos = rafFile.leerAleatorioAlumno("alumnos.raf");
        //rafFile.escribirAleatorioPorID(alumnos,"alumnos_2.raf",2);
        //List<Alumno> alumnos2 = rafFile.leerAleatorioAlumno("alumnos_2.raf");
        //for(Alumno alumno : alumnos2){
       //     System.out.println(alumno);
       // }
    }
}
