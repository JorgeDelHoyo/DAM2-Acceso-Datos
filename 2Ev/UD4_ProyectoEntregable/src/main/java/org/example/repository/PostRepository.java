package org.example.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.types.ObjectId;
import org.example.model.Comentario;
import org.example.model.Post;
import org.example.util.MongoDBUtil; // Importamos tu utilidad

import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    private final MongoCollection<Post> collection;

    public PostRepository() {
        // Usamos TU clase MongoDBUtil
        MongoDatabase db = MongoDBUtil.getDatabase();
        // Obtenemos la colección mapeada a la clase Post
        this.collection = db.getCollection("posts", Post.class);
    }

    // 1. CREATE
    public void guardar(Post post) {
        collection.insertOne(post);
    }

    // 2. READ (Todos)
    public List<Post> listarTodos() {
        List<Post> posts = new ArrayList<>();
        collection.find().into(posts);
        return posts;
    }

    // 2. READ (Por etiqueta)
    public List<Post> listarPorEtiqueta(String etiqueta) {
        List<Post> resultado = new ArrayList<>();
        // Mongo busca dentro del array automáticamente
        collection.find(Filters.eq("etiquetas", etiqueta)).into(resultado);
        return resultado;
    }

    // 3. UPDATE (Añadir Comentario - Embebido)
    public void agregarComentario(ObjectId idPost, Comentario comentario) {
        collection.updateOne(
                Filters.eq("_id", idPost),
                Updates.push("comentarios", comentario) // $push añade al array sin sobrescribir
        );
    }

    // 3. UPDATE (Modificar campo simple)
    public void actualizarTitulo(ObjectId idPost, String nuevoTitulo) {
        collection.updateOne(
                Filters.eq("_id", idPost),
                Updates.set("titulo", nuevoTitulo)
        );
    }

    // 4. DELETE
    public void eliminar(ObjectId idPost) {
        collection.deleteOne(Filters.eq("_id", idPost));
    }
}