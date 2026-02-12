package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Autor;

public class AutorRepository implements BaseRepository<Autor, Long>{
    @Override
    public Autor findById(EntityManager em, Long id) {
        return em.find(Autor.class,id);
    }

    @Override
    public void persist(EntityManager em, Autor autor) {
        em.persist(autor);
    }

    @Override
    public Autor merge(EntityManager em, Autor autor) {
        return em.merge(autor);
    }

    @Override
    public void remove(EntityManager em, Autor autor) {
        em.remove(autor);
    }
}
