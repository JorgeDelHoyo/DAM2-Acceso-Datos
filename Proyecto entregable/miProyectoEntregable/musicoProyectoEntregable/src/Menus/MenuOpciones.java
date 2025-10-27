package Menus;

import ConfiguracionMusico.MusicoConfig;
import Objetos.Musico;
import Objetos.Banda;
import Interfaces.MusicoRepositorio;

import java.text.SimpleDateFormat;
import java.util.*;

public class MenuOpciones{

    private final MusicoConfig service;
    private final Scanner sc = new Scanner(System.in);

    public MenuOpciones(MusicoConfig service) {
        this.service = service;
    }

    /**
     * Metodo para mostrar y realizar un menu interactivo
     */
    public void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENÚ MÚSICOS ===");
            System.out.println("Repositorio: " + service.getRuta());
            System.out.println("""
                1. Cargar músicos
                2. Listar músicos
                3. Añadir músico
                4. Añadir banda a músico
                5. Eliminar músico
                6. Guardar músicos
                0. Salir""");
            System.out.print("Opción: ");
            String op = sc.nextLine();

            try {
                switch (op) {
                    case "1" -> {
                        service.cargar(); // carga la lista en memoria
                        System.out.println("Datos cargados.");
                    }
                    case "2" -> listar();
                    case "3" -> crearMusico();
                    case "4" -> agregarBanda();
                    case "5" -> eliminar();
                    case "6" -> {
                        service.guardar(); // guarda la lista en memoria
                        System.out.println("Datos guardados.");
                    }
                    case "0" -> salir = true;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Metodo para listar musicos
     */
    private void listar() {
        List<Musico> lista = service.listar();
        if (lista.isEmpty()) System.out.println("No hay músicos.");
        else lista.forEach(System.out::println);
    }

    /**
     * Metodo para crear un musico
     */
    private void crearMusico() {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Instrumento: ");
            String instrumento = sc.nextLine();

            service.agregar(new Musico(id, nombre, instrumento));
            System.out.println("Músico agregado.");
        } catch (Exception e) {
            System.out.println("Formato incorrecto.");
        }
    }

    /**
     * Metodo para agregar una banda a un musico
     */
    private void agregarBanda() {
        try {
            System.out.print("ID del músico: ");
            int id = Integer.parseInt(sc.nextLine());
            Musico m = service.buscar(id);
            if (m == null) {
                System.out.println("Músico no encontrado.");
                return;
            }

            System.out.print("ID de la banda: ");
            int idBanda = Integer.parseInt(sc.nextLine());
            System.out.print("Nombre de la banda: ");
            String nombreBanda = sc.nextLine();

            m.getBandas().add(new Banda(idBanda, nombreBanda));
            System.out.println("Banda añadida al músico.");
        } catch (Exception e) {
            System.out.println("Formato incorrecto.");
        }
    }

    /**
     * Metodo para eliminar un musico
     */
    private void eliminar() {
        try {
            System.out.print("ID del músico a eliminar: ");
            int id = Integer.parseInt(sc.nextLine());
            boolean removed = service.eliminar(id);
            System.out.println(removed ? "Músico eliminado." : "Músico no encontrado.");
        } catch (Exception e) {
            System.out.println("Formato incorrecto.");
        }
    }

}
