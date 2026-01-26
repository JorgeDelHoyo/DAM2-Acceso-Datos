    package org.example.main;

    import jakarta.persistence.EntityManager;
    import jakarta.persistence.EntityTransaction;
    import org.example.model.Perfil;
    import org.example.model.Usuario;
    import org.example.util.JPAUtil;

    /**
     * Ejercicio 2
     */
    public class Main {

        public static void main(String[] args) {

            // 1. Crear y guardar (Prueba Cascade PERSIST)
            Long usuarioId = crearUsuarioConPerfil();

            // 2. Leer con sesión abierta (Prueba acceso normal)
            leerUsuarioYAccederAlPerfil(usuarioId);

            // 3. Acceder con sesión cerrada (Prueba FetchType LAZY vs EAGER)
            probarAccesoConEntityManagerCerrado(usuarioId);

            // 4. Borrar (Prueba Cascade REMOVE)
            borrarUsuarioYComprobarCascade(usuarioId);

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
                System.out.println("\n--- [A] CASCADE PERSIST ---");


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

                System.out.println("Persistiendo solo Usuario...");
                em.persist(u);

                tx.commit();

                System.out.println("Usuario creado con ID=" + u.getId());
                System.out.println("Perfil guardado automáticamente con ID=" + p.getId());
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

            Long perfilId = null;
            EntityManager em = JPAUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();

            // 1) Leer perfilId antes de borrar (para comprobar después)
            EntityManager emRead = JPAUtil.getEntityManager();
            try {
                tx.begin();

                Usuario u0 = emRead.find(Usuario.class, usuarioId);

                if( u0 != null && u0.getPerfil() != null){
                    perfilId = u0.getPerfil().getId();
                    System.out.println("Detectado perfil ID: "+perfilId+" antes de borrar.");
                }

                emRead.close();

                /*
                 * TODO 8:
                 *  - Elimina el usuario con em.remove(u)
                 */

                Usuario uParaBorrar = em.find(Usuario.class, usuarioId);

                if(uParaBorrar != null){
                    em.remove(uParaBorrar);
                }

                tx.commit();
                System.out.println("\n[D] Usuario eliminado (commit OK).");

            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            } finally {
                em.close();
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
            } catch (Exception ex) {
                System.out.println("[C] Excepción al acceder al perfil con EM cerrado:");
                System.out.println("    " + ex.getClass().getName() + " -> " + ex.getMessage());
            }
        }
    }
