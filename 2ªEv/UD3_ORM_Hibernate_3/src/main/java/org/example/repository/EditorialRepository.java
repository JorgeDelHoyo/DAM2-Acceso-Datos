package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Editorial;

public class EditorialRepository implements BaseRepository<Editorial,Long> {

    @Override
    public Editorial findById(EntityManager em, Long id) {
        return em.find(Editorial.class,id);
    }

    @Override
    public void persist(EntityManager em, Editorial editorial) {
        em.persist(editorial);
    }

    @Override
    public Editorial merge(EntityManager em, Editorial editorial) {
        return em.merge(editorial);
    }

    @Override
    public void remove(EntityManager em, Editorial editorial){
        em.remove(editorial);
    }
}
