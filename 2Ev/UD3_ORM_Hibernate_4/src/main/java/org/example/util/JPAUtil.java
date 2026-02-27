package org.example.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    // 1. Quitamos el final y la inicialización directa
    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager() {
        // 2. Solo conectamos cuando se pide
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("entrega_orm_hibernate_4");
        }
        return emf.createEntityManager();
    }

    public static void shutdown() {
        if(emf != null && emf.isOpen()){
            emf.close();
        }
    }
}