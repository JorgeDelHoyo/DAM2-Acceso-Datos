
package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.model.Producto;
import org.example.util.JPAUtil;

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

        //find() + modificación MANAGED (sin usar merge) "idL"
        modificarProductoManaged(1L);

        //detach(): Modificar una entidad DETACHED NO debe persistir
        //merge() Volver a asociar una entidad DETACHED
        //probarDetach(productoId);

        //remove() Eliminar la entidad
        //eliminarProducto(productoId);

        // Cierre final del EntityManagerFactory
        JPAUtil.shutdown();
    }

    /**
     * Crear un producto y persistirlo
     */
    private static Long crearProducto() {
        // Recuperamos de JPAUtil una instancia del entityManager
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        

        try {
            tx.begin();

            Producto p = new Producto("Baliza v16", 45.99);
            System.out.println("Producto creado en estado TRANSIENT");
            p.toString();

            // Persisto el producto, estado: MANAGED
            em.persist(p);
            tx.commit();
            System.out.println("Producto persistido en estado MANAGED");
            p.toString();

            return p.getId();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close(); // al cerrar, la entidad pasa a DETACHED
        }
    }

    /**
     * find() + modificar estando MANAGED
     */
    private static void modificarProductoManaged(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Producto pBuscado = em.find(Producto.class, id);
            System.out.println("Producto recuperado en estado MANAGED");
            pBuscado.toString();

            System.out.println("Buscamos producto con FIND");
            pBuscado.setPrecio(19.99);
            System.out.println("Producto modificado en estado MANAGED");
            pBuscado.toString();

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
//
//    /**
//     * detach()
//     */
//    private static void probarDetach(Long id) {
//        // Inicializamos entityManager y la transacción
//
//        try {
//
//            /*
//             *  - Recuperar el producto con find()
//             *  - Llamar a detach()
//             */
//
//            /*
//             *  - Modificar el precio estando DETACHED
//             *  - Mostrar por consola el nuevo valor
//             */
//
//            tx.commit();
//            /*
//             *  - Indicar que NO debería haberse guardado en BBDD
//             */
//
//            /*
//             *  - Llamar a merge()
//             *  - Guardar la referencia devuelta (entidad MANAGED)
//             */
//
//            tx.commit();
//            /*
//             * TODO 13:
//             *  - Indicar que el cambio ahora SÍ debe persistirse
//             */
//
//        } catch (Exception e) {
//            if (tx.isActive()) tx.rollback();
//            throw e;
//        } finally {
//            em.close();
//        }
//    }
//
//    /**
//     * remove()
//     */
//    private static void eliminarProducto(Long id) {
//        EntityManager em = JPAUtil.getEntityManager();
//        EntityTransaction tx = em.getTransaction();
//
//        try {
//            tx.begin();
//
//            /*
//             *  - Recuperar el producto con find()
//             *  - Llamar a remove()
//             */
//
//            tx.commit();
//            /*
//             *  - Indicar que el producto debe haberse eliminado
//             */
//
//        } catch (Exception e) {
//            if (tx.isActive()) tx.rollback();
//            throw e;
//        } finally {
//            em.close();
//        }
//    }
}
