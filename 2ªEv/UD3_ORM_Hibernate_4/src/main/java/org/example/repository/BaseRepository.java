package org.example.repository;

import jakarta.persistence.EntityManager;

// T = Tipo de la entidad
// ID = Tipo de la clave primaria
public interface BaseRepository<T,ID> {

    // Buscar entidad por ID
    T findById(EntityManager em, ID id);

    //Guardar nueva entidad
    void persist(EntityManager em, T entity);

    // Actualizar entidad existente
    T merge(EntityManager em, T entity);

    // Borrar entidad
    void remove(EntityManager em, T entity);
}