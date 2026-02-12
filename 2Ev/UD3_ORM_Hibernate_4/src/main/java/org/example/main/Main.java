package org.example.main;

import org.example.service.BibliotecaService;
import org.example.util.JPAUtil;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // 1. Instanciamos el servicio (el "Director de Orquesta")
        BibliotecaService service = new BibliotecaService();

        System.out.println("=== INICIO DE PRUEBAS FINAL (RELACIONES N:M) ===");

        // -----------------------------------------------------------------------
        // FASE 1: PREPARACIÓN DEL ESCENARIO (Crear Editorial y Autores sueltos)
        // -----------------------------------------------------------------------
        System.out.println("\n--- [PASO 1] Creando datos maestros (Editorial y Autores) ---");

        // Creamos una Editorial
        Long idEditorial = service.crearEditorial("Editorial Minotauro", "España");
        System.out.println("-> Editorial creada con ID: " + idEditorial);

        // Creamos dos Autores
        Long idTolkien = service.crearAutor("J.R.R. Tolkien", "Reino Unido");
        Long idKing = service.crearAutor("Stephen King", "EEUU");
        System.out.println("-> Autores creados: Tolkien (ID " + idTolkien + "), King (ID " + idKing + ")");


        // -----------------------------------------------------------------------
        // FASE 2: PROBAR 'AddLibroConAutores' (El método de 16 Puntos)
        // -----------------------------------------------------------------------
        System.out.println("\n--- [PASO 2] Creando Libro complejo con Autores asignados ---");

        // Vamos a crear "El Señor de los Anillos" y asignarle a Tolkien directamente.
        // Preparamos la lista de IDs de autores (en este caso solo uno)
        List<Long> listaAutores1 = Arrays.asList(idTolkien);

        service.addLibroConAutores(
                idEditorial,                // ID Editorial
                "El Señor de los Anillos",  // Título
                "978-84-450",               // ISBN
                29.95,                      // Precio
                listaAutores1               // Lista de Autores
        );


        // -----------------------------------------------------------------------
        // FASE 3: PROBAR 'addAutorALibro' (Asignar autor a posteriori)
        // -----------------------------------------------------------------------
        System.out.println("\n--- [PASO 3] Añadiendo un segundo autor a un libro existente ---");

        // Vamos a crear un libro de "Colaboración" primero solo con King
        List<Long> listaAutores2 = Arrays.asList(idKing);
        service.addLibroConAutores(idEditorial, "Talismán", "111-222", 19.90, listaAutores2);

        // El libro "Talismán" será el ID 2 (porque es el segundo que creamos).
        // Ahora le añadimos a Peter Straub (imaginemos que Tolkien ayuda a escribirlo para probar)
        // Usaremos el método 'addAutorALibro'
        System.out.println("... Añadiendo a Tolkien como co-autor del libro ID 2 ...");
        service.addAutorALibro(idTolkien, 2L);


        // -----------------------------------------------------------------------
        // FASE 4: CONSULTAS (Verificar que el N:M funciona en ambos sentidos)
        // -----------------------------------------------------------------------

        // A) Preguntar al LIBRO quiénes son sus autores
        System.out.println("\n--- [PASO 4.A] Listar Autores del Libro 'Talismán' (ID 2) ---");
        service.listarAutoresLibro(2L); // Debería salir King y Tolkien

        // B) Preguntar al AUTOR qué libros ha escrito
        System.out.println("\n--- [PASO 4.B] Listar Libros de Tolkien (ID " + idTolkien + ") ---");
        // Deberían salir "El Señor de los Anillos" y "Talismán"
        service.listarLibrosAutor(idTolkien);

        // Cerramos
        JPAUtil.shutdown();
        System.out.println("\n=== FIN DE LAS PRUEBAS ===");
    }
}