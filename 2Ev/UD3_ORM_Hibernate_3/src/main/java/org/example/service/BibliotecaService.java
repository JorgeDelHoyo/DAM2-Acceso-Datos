package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.model.Editorial;
import org.example.model.Libro;
import org.example.repository.EditorialRepository;
import org.example.repository.LibroRepository;
import org.example.util.JPAUtil;

public class BibliotecaService {

    private final EditorialRepository editorialRepository = new EditorialRepository();
    private final LibroRepository libroRepository = new LibroRepository();

    /**
     * CASO 1: Crear Datos Iniciales
     * Crea 1 Editorial y 2 Libros, los asocia y los guarda.
     * Gracias al CascadeType.ALL en Editorial, al guardar la editorial se guardan los libros.
     */
    public Long crearDatosIniciales() {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Crear objeto
            Editorial editorial = new Editorial("Editorial ANAYA", "España");
            Libro l1 = new Libro("Matemáticas académicas", "123-456",25.80);
            Libro l2 = new Libro("Historia del arte","789-101", 20.10);

            // Usar HELPER para bidireccionalidad
            editorial.addLibro(l1);
            editorial.addLibro(l2);

            // Persistir la Editorial
            editorialRepository.persist(em,editorial);

            tx.commit();
            return editorial.getId();

        } catch (Exception e) {
            if(tx.isActive()){tx.rollback();}
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * CASO 2: AddLibro
     * Busca una editorial existente y le añade un nuevo libro.
     */
    public void addLibro(Long idEditorial, String titulo, String isbn, Double precio) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Buscar editorial
            Editorial editorial = editorialRepository.findById(em, idEditorial);

            if (editorial != null) {
                // Crear nuevo libro
                Libro nuevoLibro = new Libro(titulo,isbn,precio);

                // HELPER para guardar FK correctamente
                editorial.addLibro(nuevoLibro);

                // También se puede hacer libroRepository.persist(em, nuevoLibro)
                editorialRepository.merge(em,editorial);

                System.out.println("Libro añadido a la editorial: " + editorial.getNombre());
            } else {
                System.out.println("Editorial no encontrada con ID: "+idEditorial);
            }

            tx.commit();

        } catch (Exception e) {
            if(tx.isActive()){tx.rollback();}
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * CASO 3: ListaLibrosEditorial
     * Devuelve la lista de libros de una editorial.
     * IMPORTANTE: Al ser LAZY, hay que inicializar la lista antes de cerrar el EM.
     */
    public void listaLibrosEditorial(long idEditorial) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Editorial editorial = editorialRepository.findById(em, idEditorial);

            if(editorial != null) {
                System.out.println("Libros de la editorial "+editorial.getNombre());
                for(Libro l : editorial.getLibros()){
                    System.out.println(" - " + l.getTitulo() + " (" + l.getPrecio() + "€)");
                }
            } else {
                System.out.println("No existe editorial con ID " + idEditorial);
            }
        } finally {
            em.close();
        }
    }

    /**
     * CASO 4: BuscarLibro
     */
    public void buscarLibro(Long idLibro) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Libro libro = libroRepository.findById(em, idLibro);
            if(libro != null) {
                System.out.println("Libro encontrado: " + libro.getTitulo() +
                        ", Editorial: " + libro.getEditorial().getNombre());
            } else {
                System.out.println("Libro no encontrado");
            }
        } finally {
            em.close();
        }
    }
}
