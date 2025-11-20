package Menus;

import Objetos.Banda;
import Objetos.Musico;
import service.BandaService;
import service.MusicoService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuOpciones {

    private final MusicoService musicoService;
    private final BandaService bandaService; // Podría ser útil para futuras opciones
    private final Scanner sc = new Scanner(System.in);

    public MenuOpciones(MusicoService musicoService, BandaService bandaService) {
        this.musicoService = musicoService;
        this.bandaService = bandaService;
    }

    public void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENÚ GESTOR DE MÚSICOS ===");
            System.out.println("""
                    1. Listar músicos
                    2. Añadir músico
                    3. Actualizar músico (y añadirle bandas)
                    4. Eliminar músico
                    0. Salir""");
            System.out.print("Opción: ");
            String op = sc.nextLine();

            try {
                switch (op) {
                    case "1" -> listarMusicos();
                    case "2" -> crearMusico();
                    case "3" -> actualizarMusico();
                    case "4" -> eliminarMusico();
                    case "0" -> salir = true;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (SQLException e) {
                System.err.println("Error de base de datos: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
            }
        }
    }

    private void listarMusicos() throws SQLException {
        List<Musico> lista = musicoService.obtenerTodosLosMusicos();
        if (lista.isEmpty()) {
            System.out.println("No hay músicos para mostrar.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void crearMusico() throws SQLException {
        try {
            System.out.print("Nombre del músico: ");
            String nombre = sc.nextLine();
            System.out.print("Instrumento: ");
            String instrumento = sc.nextLine();

            Musico nuevoMusico = new Musico(0, nombre, instrumento); // El ID se autogenera
            musicoService.guardarMusico(nuevoMusico);
            System.out.println("Músico agregado con éxito. Nuevo ID: " + nuevoMusico.getIdMusico());
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
        }
    }

    private void actualizarMusico() throws SQLException {
        try {
            System.out.print("ID del músico a actualizar: ");
            int id = Integer.parseInt(sc.nextLine());

            Optional<Musico> musicoOpt = musicoService.obtenerMusicoPorId(id);
            if (musicoOpt.isEmpty()) {
                System.out.println("Músico no encontrado.");
                return;
            }
            Musico musico = musicoOpt.get();
            System.out.println("Músico encontrado: " + musico);

            System.out.print("Nuevo nombre (dejar en blanco para no cambiar): ");
            String nombre = sc.nextLine();
            if (!nombre.isBlank()) {
                musico.setNombre(nombre);
            }

            System.out.print("Nuevo instrumento (dejar en blanco para no cambiar): ");
            String instrumento = sc.nextLine();
            if (!instrumento.isBlank()) {
                musico.setInstrumento(instrumento);
            }

            // Opción para añadir bandas
            System.out.print("¿Añadir nueva banda? (s/n): ");
            if (sc.nextLine().equalsIgnoreCase("s")) {
                System.out.print("Nombre de la nueva banda: ");
                String nombreBanda = sc.nextLine();
                // En un sistema real, buscaríamos si la banda ya existe con bandaService
                Banda nuevaBanda = new Banda(0, nombreBanda);
                musico.getBandas().add(nuevaBanda);
            }

            musicoService.actualizarMusico(musico);
            System.out.println("Músico actualizado correctamente.");

        } catch (NumberFormatException e) {
            System.err.println("ID no válido. Debe ser un número.");
        }
    }

    private void eliminarMusico() throws SQLException {
        try {
            System.out.print("ID del músico a eliminar: ");
            int id = Integer.parseInt(sc.nextLine());
            musicoService.eliminarMusico(id);
            System.out.println("Músico con ID " + id + " eliminado (si existía).");
        } catch (NumberFormatException e) {
            System.err.println("ID no válido. Debe ser un número.");
        }
    }
}
