package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Libro;

public class LibroRepository implements BaseRepository<Libro,Long> {

    @Override
    public Libro findById(EntityManager em, Long id){
        return em.find(Libro.class,id);
    }

    @Override
    public void persist(EntityManager em, Libro libro) {
        em.persist(libro);
    }

    @Override
    public Libro merge (EntityManager em, Libro libro) {
        return em.merge(libro);
    }

    @Override
    public void remove (EntityManager em, Libro libro) {
        em.remove(libro);
    }
}
