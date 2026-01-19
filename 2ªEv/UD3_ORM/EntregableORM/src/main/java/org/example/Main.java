package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.Model.Producto;
import org.example.Util.JPAUtil;

/**
 * Ejercicio 1 – Ciclo de vida de una entidad
 *
 * En este main se deben probar, en orden:
 *  - persist()
 *  - find()
 *  - modificación de entidad MANAGED
 *  - detach()
 *  - merge()
 *  - remove()
 *
 * Sigue los pasos y completa SOLO donde se indica.
 */
public class Main {

    public static void main(String[] args) {

        Long productoId;

        // persist(): transient → managed → commit
        productoId = crearProducto();

        //find() + modificación MANAGED  (sin usar merge)
        modificarProductoManaged(productoId);

        //detach(): Modificar una entidad DETACHED NO debe persistir
        //merge() Volver a asociar una entidad DETACHED
        probarDetach(productoId);

        //remove() Eliminar la entidad
        eliminarProducto(productoId);

        // Cierre final del EntityManagerFactory
        JPAUtil.shutdown();
    }

    /**
     * Crear un producto y persistirlo
     */
    private static Long crearProducto() {
        // Recuperamos de JPAUtil una instancia del entityManager
        EntityManager em = JPAUtil.getEntityManager();
        
        // Instanciamos una transacción
        EntityTransaction et = em.getTransaction();

        try {
            // Iniciar una transacción
            et.begin();
            System.out.println("\n--- 1. CREAR PRODUCTO --- (PERSIST)");

            // Crear objeto nuevo
            Producto p = new Producto("Gel hidroalcohólico", 1.5);
            System.out.println("Producto en estado TRANSIENT: "+p);

            // Guardar instancia producto
            em.persist(p);
            System.out.println("Estado MANAGED (aun no en BBDD): "+p);

            // Hacer un commit
            et.commit(); // Se ejecuta el INSERT
            System.out.println("Estado PERSISTED (ID generado): "+p.getId());

            //em.close();

            return p.getId();

        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw e;
        } finally {
            em.close(); // al cerrar, la entidad pasa a DETACHED
        }
    }

    /**
     * find() + modificar estando MANAGED
     */
    private static void modificarProductoManaged(Long id) {

        // Inicializamos entityManager y la transacción
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();

        try{
            et.begin();
            System.out.println("\n--- 2. MODIFICAR MANAGED (DIRTY CHECKING) ---");

            // Recuperar el producto
            Producto p = em.find(Producto.class, id);
            System.out.println("Producto recuperado: "+p);

            // Modificar precio SIN merge
            p.setPrecio(2.50);
            System.out.println("Precio modificado en memoria a: "+p.getPrecio());

            // Al hacer commit JPA detecta que 'p' está Managed y cambió
            // Lanza UPDATE automáticamente
            et.commit();

            // Indicar confirmación
            System.out.println("Commit realizado. El precio se actualizó en BBDD automáticamente.");

        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * detach() y merge()
     */
    private static void probarDetach(Long id) {
        // Inicializamos entityManager y la transacción
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            System.out.println("\n --- 3. DETACH Y MERGE ---");
            et.begin();

            // Recuperar y DETACH
            Producto p = em.find(Producto.class, id);
            em.detach(p); // El EntityManager deja de vigilar a 'p'

            // Modificar estando DETACHED
            p.setPrecio(100.0);
            System.out.println("Modificado en DETACHED (Precio: 100.0). Hacemos commit...");

            et.commit(); // No hay cambios en BBDD porque p no está gestionado
            System.out.println("Commit hecho. NO debería haber UPDATE en consola (mira los logs SQL).");

            // --- MERGE ---
            // Necesitamos nueva transacción tras el commit anterior
            et.begin();

            System.out.println("Ejecutando MERGE...");
            // merge no vuelve a hacer managed a 'p', sino que devuelve una copia gestionada
            Producto pGestionado = em.merge(p);

            et.commit(); // Ahora sí hay UPDATE
            System.out.println("Commit tras Merge. El precio ahora sí es: "+pGestionado.getPrecio());

        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * remove()
     */
    private static void eliminarProducto(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            System.out.println("\n--- 4. REMOVE ---");
            et.begin();

            // Para eliminar, la entidad debe estar MANAGED
            Producto p = em.find(Producto.class, id);

            em.remove(p);
            System.out.println("Entidad marcada para eliminación (Removed).");

            et.commit(); // Se ejecuta el DELETE
            System.out.println("Producto eliminado de la BBDD.");

        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}