package org.example.main;

import org.example.service.BibliotecaService;
import org.example.util.JPAUtil;

public class Main {

    public static void main(String[] args) {

        // Instanciamos el Servicio
        BibliotecaService service = new BibliotecaService();

        // PRUEBA 1: Crear Datos Iniciales
        System.out.println("\n--- [1] Creando Editorial y Libros iniciales ---");
        Long idEditorial = service.crearDatosIniciales();

        if (idEditorial != null) {
            System.out.println("Editorial creada con ID: " + idEditorial);
        } else {
            System.out.println("ERROR. No se ha podido crear la editorial.");
            JPAUtil.shutdown();
            return;
        }

        // PRUEBA 2: Listar libros (Verificar que se guardaron en cascada)
        System.out.println("\n--- [2] Listando libros de la Editorial creada ---");
        service.listaLibrosEditorial(idEditorial);


        // PRUEBA 3: Añadir un libro nuevo a una editorial existente
        System.out.println("\n--- [3] Añadiendo un libro nuevo ---");
        // Añadimos a la editorial que acabamos de crear (idEditorial)
        service.addLibro(idEditorial, "Manual de Spring Boot", "999-000-111", 35.00);


        // PRUEBA 4: Volver a listar (Para ver si aparece el nuevo)
        System.out.println("\n--- [4] Listando libros de nuevo (Debe haber 3) ---");
        service.listaLibrosEditorial(idEditorial);


        // PRUEBA 5: Buscar un libro por su ID
        System.out.println("\n--- [5] Buscando un libro específico (ID 1) ---");
        service.buscarLibro(1L);

        JPAUtil.shutdown();
    }
}