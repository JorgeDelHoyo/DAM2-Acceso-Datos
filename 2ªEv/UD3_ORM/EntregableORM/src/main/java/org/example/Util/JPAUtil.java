package org.example.Util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase de utilidad para gestionar el EntityManagerFactory.
 *
 * - Debe existir UNA sola instancia de EntityManagerFactory por aplicación
 * - Cada operación creará su propio EntityManager
 */
public class JPAUtil {

    /**
     * EntityManagerFactory compartido
     */
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("orm-clase");

    /**
     * Devuelve un EntityManager nuevo
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Cierra correctamente el EntityManagerFactory
     * Este metodo se llamará al final del main cuando el programa ha terminado
     */
    public static void shutdown() {
        if(emf.isOpen()){
            emf.close();
        }
    }
}
