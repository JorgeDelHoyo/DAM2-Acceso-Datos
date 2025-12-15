package Interfaces;

import Objetos.Musico;

import java.util.List;
import java.util.Optional;

public interface MusicoRepositorio {
    /**
     * Carga todos los musicos del repositorio.
     *
     * @return Lista de todos los musicos.
     * @throws Exception Si ocurre un error.
     */
    List<Musico> cargar() throws Exception;

    /**
     * Guarda una lista completa de musicos.
     * Este método es útil para una carga inicial o para persistencia basada en ficheros.
     * Para bases de datos, es preferible usar los métodos CRUD.
     *
     * @param musicos Lista de musicos a guardar.
     * @throws Exception Si ocurre un error.
     */
    void guardar(List<Musico> musicos) throws Exception;

    /**
     * Busca un musico por su ID.
     * @param id El ID del musico a buscar.
     * @return Un Optional que contiene al musico si se encuentra, o vacío si no.
     * @throws Exception Si ocurre un error.
     */
    Optional<Musico> buscarPorId(int id) throws Exception;

    /**
     * Añade un nuevo musico al repositorio.
     * @param musico El musico a añadir.
     * @return El musico guardado (puede tener un nuevo ID asignado por la BD).
     * @throws Exception Si ocurre un error.
     */
    Musico añadir(Musico musico) throws Exception;

    /**
     * Modifica un musico existente.
     * @param musico El musico con los datos actualizados.
     * @return true si se modificó correctamente, false si no.
     * @throws Exception Si ocurre un error.
     */
    boolean modificar(Musico musico) throws Exception;

    /**
     * Elimina un musico por su ID.
     * @param id El ID del musico a eliminar.
     * @return true si se eliminó correctamente, false si no.
     * @throws Exception Si ocurre un error.
     */
    boolean eliminar(int id) throws Exception;

    /**
     * Devuelve la ruta o identificador del repositorio.
     * @return Una cadena que identifica el repositorio (ruta de fichero, URL de BD, etc.).
     */
    String getRuta();
}
