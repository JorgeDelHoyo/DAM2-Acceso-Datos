package org.example.main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.util.JPAUtil;
import org.example.model.Usuario;
import org.example.model.Perfil;

/**
 * Ejercicio 2
 */
public class Main {

    public static void main(String[] args) {

        Long usuarioId = crearUsuarioConPerfil();

        //leerUsuarioYAccederAlPerfil(usuarioId);

        probarAccesoConEntityManagerCerrado(usuarioId);

        //borrarUsuarioYComprobarCascade(usuarioId);

        JPAUtil.shutdown();
    }

    /**
     * PASO A: Crear Usuario + Perfil y persistir SOLO el usuario.
     * - Si cascade incluye PERSIST, el perfil se persistirá automáticamente.
     */
    private static Long crearUsuarioConPerfil() {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Usuario u = new Usuario("Ana");
            Perfil p = new Perfil("Bio de Ana", "600123123");

            /*
             * TODO 1:
             *  - Asocia el perfil al usuario (lado dueño)
             *    u.setPerfil(p);
             */
            u.setPerfil(p);

            /*
             * TODO 2 (solo si implementas bidireccionalidad):
             *  - Mantén consistencia en memoria:
             *    p.setUsuario(u);
             */
            p.setUsuario(u);

            /*
             * TODO 3:
             *  - Persiste SOLO el usuario
             *    em.persist(u);
             */
            System.out.println("Intentando persistir Usuario (y arrastrar Perfil)...");
            em.persist(u);

            tx.commit();

            System.out.println("[A] Usuario creado con id=" + u.getId());
            return u.getId();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * PASO B: Borrar usuario y comprobar el efecto del cascade.
     * - Con cascade=PERSIST: se borra usuario, pero el perfil normalmente queda en la tabla perfiles.
     * - Con cascade=ALL o REMOVE: se borra usuario y su perfil.
     */
    private static void borrarUsuarioYComprobarCascade(Long usuarioId) {
        // 1) Leer perfilId antes de borrar (para comprobar después)
        EntityManager emRead = JPAUtil.getEntityManager();
        EntityTransaction tx = emRead.getTransaction();

        Long perfilId = null;

        try {
            tx.begin();

            Usuario u0 = emRead.find(Usuario.class, usuarioId);

            // Guardar ID del perifl antes de borrar para buscarlo luego
            if( u0 != null && u0.getPerfil() != null){
                perfilId = u0.getPerfil().getId();
            }

            /*
             * TODO 8:
             *  - Elimina el usuario con em.remove(u)
             */
            if (u0 != null) {
                System.out.println("Borrando usuario ID: "+usuarioId);
                emRead.remove(u0);
            }

            tx.commit();
            System.out.println("\n[D] Usuario eliminado (commit OK).");

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            emRead.close();
        }
    }

    /**
     * PASO C: Leer Usuario y acceder al Perfil.
     * - Con EAGER normalmente se carga inmediatamente.
     * - Con LAZY se carga al acceder (si EM sigue abierto).
     */
    private static void leerUsuarioYAccederAlPerfil(Long usuarioId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Usuario u = em.find(Usuario.class, usuarioId);
            System.out.println("\n[B] Usuario leído: " + u);

            /*
             * TODO 4:
             *  - Accede al perfil y muéstralo por consola
             *    System.out.println("[B] Perfil: " + u.getPerfil());
             */
            System.out.println("Accediendo el perfil...");
            // Al hacer getPerfil() y tocar un dato (como getBio) forzamos la carga si es LAZY
            System.out.println("[B] Perfil: "+ u.getPerfil().getBio());

        } finally {
            em.close();
        }
    }

    /**
     * PASO D: Probar acceso a la relación con el EntityManager cerrado.
     * - Si fetch=LAZY, es posible que salte una excepción al hacer u.getPerfil()
     *   fuera de sesión.
     */
    private static void probarAccesoConEntityManagerCerrado(Long usuarioId) {

        Usuario u;

        EntityManager em = JPAUtil.getEntityManager();
        try {
            u = em.find(Usuario.class, usuarioId);
            System.out.println("\n[C] Usuario cargado (aún con EM abierto): " + u);

            /*
             * TODO 5 (opcional):
             *  - Si quieres evitar la excepción en LAZY, fuerza la carga aquí:
             *    u.getPerfil();
             *  - Pero para “provocarla”, NO llames a getPerfil aquí.
             */

        } finally {
            em.close();
        }

        System.out.println("[C] EntityManager cerrado. Intentando acceder al perfil...");

        try {
            /*
             * TODO 6:
             *  - Accede al perfil con EM cerrado:
             *    System.out.println(u.getPerfil());
             */
            System.out.println(u.getPerfil());
        } catch (Exception ex) {
            System.out.println("[C] Excepción al acceder al perfil con EM cerrado:");
            System.out.println("    " + ex.getClass().getName() + " -> " + ex.getMessage());
        }
    }
}