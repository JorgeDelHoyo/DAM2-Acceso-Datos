package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.util.JPAUtil;
import org.example.model.Autor;
import org.example.model.Editorial;
import org.example.model.Libro;
import org.example.repository.AutorRepository;
import org.example.repository.EditorialRepository;
import org.example.repository.LibroRepository;

import java.util.List;

public class BibliotecaService {

    // Instanciamos los 3 repositorios para poder "jugar" con todos los datos
    private final LibroRepository libroRepository = new LibroRepository();
    private final AutorRepository autorRepository = new AutorRepository();

    // Si decides NO usar editorial, borra esta línea y las referencias a ella
    private final EditorialRepository editorialRepository = new EditorialRepository();

    /**
     * MÉTODO 1: addAutorALibro(autorId, libroId)
     * Une un autor existente con un libro existente.
     */
    public void addAutorALibro(Long autorId, Long libroId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Autor autor = autorRepository.findById(em, autorId);
            Libro libro = libroRepository.findById(em, libroId);

            if (autor != null && libro != null) {
                // Usamos el helper del Libro (que es quien manda en la relación N:M)
                libro.addAutor(autor);

                // Guardamos el libro. Al tener Cascade, se actualiza la tabla intermedia 'libro_autor'
                libroRepository.merge(em, libro);
                System.out.println(">> OK: Autor " + autor.getNombre() + " asignado al libro " + libro.getTitulo());
            } else {
                System.out.println(">> ERROR: Autor o Libro no encontrados.");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * MÉTODO 2: AddLibroConAutores
     * Este es el complejo: Une Editorial (1) -> Libro (N) -> Autores (M)
     */
    public void addLibroConAutores(Long idEditorial, String titulo, String isbn, Double precio, List<Long> listaAutoresIds) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 1. Buscamos la Editorial (El padre del libro)
            Editorial editorial = editorialRepository.findById(em, idEditorial);

            if (editorial != null) {
                // 2. Creamos el Libro nuevo
                Libro nuevoLibro = new Libro(titulo, isbn, precio);

                // 3. Asociamos Libro -> Editorial (Relación 1:N)
                editorial.addLibro(nuevoLibro);

                // 4. Asociamos Libro <-> Autores (Relación N:M)
                // Recorremos la lista de IDs que nos pasan (ej: 1, 5, 8)
                for (Long idAutor : listaAutoresIds) {
                    Autor autor = autorRepository.findById(em, idAutor);
                    if (autor != null) {
                        nuevoLibro.addAutor(autor); // Helper N:M
                    } else {
                        System.out.println("   Aviso: Autor ID " + idAutor + " no existe.");
                    }
                }

                // 5. Guardamos
                // Al guardar la Editorial (merge), por cascada se guarda el Libro,
                // y por cascada del Libro se guardan las relaciones con Autores.
                editorialRepository.merge(em, editorial);

                System.out.println(">> ÉXITO: Libro '" + titulo + "' creado en '" + editorial.getNombre() +
                        "' con " + nuevoLibro.getAutores().size() + " autores.");
            } else {
                System.out.println(">> ERROR: No existe la editorial con ID " + idEditorial);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * MÉTODO 3: listarAutoresLibro
     */
    public void listarAutoresLibro(Long libroId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Libro libro = libroRepository.findById(em, libroId);
            if (libro != null) {
                System.out.println("\nAutores del libro '" + libro.getTitulo() + "':");
                for (Autor a : libro.getAutores()) {
                    System.out.println(" - " + a.getNombre() + " (" + a.getNacionalidad() + ")");
                }
            } else {
                System.out.println("Libro no encontrado.");
            }
        } finally {
            em.close();
        }
    }

    /**
     * MÉTODO 4: listarLibrosAutor
     */
    public void listarLibrosAutor(Long autorId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Autor autor = autorRepository.findById(em, autorId);
            if (autor != null) {
                System.out.println("\nLibros escritos por " + autor.getNombre() + ":");
                for (Libro l : autor.getLibros()) {
                    System.out.println(" - " + l.getTitulo());
                }
            } else {
                System.out.println("Autor no encontrado.");
            }
        } finally {
            em.close();
        }
    }

    // --- HELPERS PARA CREAR DATOS DE PRUEBA RÁPIDAMENTE ---
    public Long crearAutor(String nombre, String pais) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Autor a = new Autor(nombre, pais);
            autorRepository.persist(em, a);
            tx.commit();
            return a.getId();
        } catch (Exception e) { if (tx.isActive()) tx.rollback(); return null; } finally { em.close(); }
    }

    public Long crearEditorial(String nombre, String pais) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Editorial e = new Editorial(nombre, pais);
            editorialRepository.persist(em, e);
            tx.commit();
            return e.getId();
        } catch (Exception ex) { if (tx.isActive()) tx.rollback(); return null; } finally { em.close(); }
    }
}