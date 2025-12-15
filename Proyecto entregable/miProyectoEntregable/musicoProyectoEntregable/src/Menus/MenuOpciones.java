package Menus;

import ConfiguracionMusico.MusicoConfig;
import Objetos.Banda;
import Objetos.Musico;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuOpciones {

    private final MusicoConfig service;
    private final Scanner sc = new Scanner(System.in);

    public MenuOpciones(MusicoConfig service) {
        this.service = service;
    }

    public void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENÚ MÚSICOS Y BANDAS ===");
            System.out.println("Repositorio: " + service.getRuta());
            System.out.println("""
                1. Listar músicos
                2. Añadir músico
                3. Añadir banda a músico
                4. Eliminar músico
                5. Buscar músico por ID
                0. Salir""");
            System.out.print("Opción: ");
            String op = sc.nextLine();

            try {
                switch (op) {
                    case "1" -> listar();
                    case "2" -> crearMusico();
                    case "3" -> agregarBanda();
                    case "4" -> eliminar();
                    case "5" -> buscar();
                    case "0" -> salir = true;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace(); // Útil para depuración
            }
        }
    }

    private void listar() throws Exception {
        List<Musico> lista = service.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay músicos registrados.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void crearMusico() throws Exception {
        System.out.print("Nombre del músico: ");
        String nombre = sc.nextLine();
        System.out.print("Instrumento: ");
        String instrumento = sc.nextLine();

        Musico nuevoMusico = new Musico(0, nombre, instrumento);
        Musico musicoGuardado = service.agregar(nuevoMusico);
        
        System.out.println("Músico agregado con ID: " + musicoGuardado.getIdMusico());
    }

    private void buscar() throws Exception {
        System.out.print("ID del músico a buscar: ");
        int id = Integer.parseInt(sc.nextLine());
        Optional<Musico> musicoOpt = service.buscar(id);

        if (musicoOpt.isPresent()) {
            System.out.println("Músico encontrado:");
            System.out.println(musicoOpt.get());
        } else {
            System.out.println("Músico con ID " + id + " no encontrado.");
        }
    }

    private void eliminar() throws Exception {
        System.out.print("ID del músico a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean removed = service.eliminar(id);
        System.out.println(removed ? "Músico eliminado." : "Músico no encontrado.");
    }

    private void agregarBanda() throws Exception {
        System.out.print("ID del músico al que quieres añadir una banda: ");
        int idMusico = Integer.parseInt(sc.nextLine());
        
        Optional<Musico> musicoOpt = service.buscar(idMusico);
        if (musicoOpt.isEmpty()) {
            System.out.println("Músico no encontrado.");
            return;
        }
        
        Musico musico = musicoOpt.get();
        System.out.println("Músico seleccionado: " + musico.getNombre());
        
        System.out.print("Nombre de la nueva banda: ");
        String nombreBanda = sc.nextLine();
        
        // Creamos un nuevo objeto Banda. El ID se autogenerará en la BD.
        Banda nuevaBanda = new Banda(0, nombreBanda);
        
        // Añadimos la nueva banda a la lista de bandas del músico
        musico.getBandas().add(nuevaBanda);
        
        // Guardamos los cambios en el músico
        service.modificar(musico);
        
        System.out.println("Banda '" + nombreBanda + "' añadida al músico '" + musico.getNombre() + "'.");
    }
}
