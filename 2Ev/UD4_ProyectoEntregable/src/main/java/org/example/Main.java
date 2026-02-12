package org.example;

import org.bson.types.ObjectId;
import org.example.model.Comentario;
import org.example.model.Post;
import org.example.repository.PostRepository;
import org.example.util.MongoDBUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO BLOG (MONGODB) ---");

        PostRepository repo = new PostRepository();

        // -----------------------------
        // 1. CREATE (Insertar Post)
        // -----------------------------
        Post post1 = new Post();
        post1.setTitulo("Aprobando Acceso a Datos");
        post1.setMensaje("MongoDB es muy flexible con los esquemas.");
        post1.setFechaPublicacion(new Date());
        post1.setEtiquetas(Arrays.asList("mongodb", "java", "examen"));
        post1.setComentarios(new ArrayList<>()); // Lista vacía inicial

        repo.guardar(post1);
        System.out.println("Post guardado con ID: " + post1.getId());

        // Guardamos el ID para usarlo en las pruebas siguientes
        ObjectId miPostId = post1.getId();

        // -----------------------------
        // 2. READ (Listar)
        // -----------------------------
        System.out.println("\n--- Listado Inicial ---");
        repo.listarTodos().forEach(System.out::println);

        System.out.println("\n--- Buscando por etiqueta 'java' ---");
        repo.listarPorEtiqueta("java").forEach(p -> System.out.println(p.getTitulo()));

        // -----------------------------
        // 3. UPDATE (Añadir Comentario)
        // -----------------------------
        System.out.println("\n--- Añadiendo comentario ---");
        Comentario c = new Comentario("¡Excelente explicación!");
        repo.agregarComentario(miPostId, c);

        // -----------------------------
        // 3. UPDATE (Modificar Título)
        // -----------------------------
        repo.actualizarTitulo(miPostId, "Aprobando Acceso a Datos (Editado)");

        // Verificamos los cambios
        System.out.println("\n--- Post tras actualizaciones ---");
        repo.listarTodos().forEach(System.out::println);

        // -----------------------------
        // 4. DELETE
        // -----------------------------
        System.out.println("\n--- Eliminando post ---");
        repo.eliminar(miPostId);

        // Comprobación final
        System.out.println("Total posts tras eliminar: " + repo.listarTodos().size());

        // Cerramos conexión al terminar
        MongoDBUtil.close();
    }
}