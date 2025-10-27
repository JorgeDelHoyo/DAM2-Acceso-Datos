package Interfaces;

import Objetos.Musico;

import java.io.IOException;
import java.util.List;

public interface MusicoRepositorio {
    /**
     * Metodo que debe implementar cualquier clase que use musicos.
     *
     * @return Devuelve la lista de musicos guardada en el archivo o repositorio.
     * @throws Exception Lanza excepción si ocurre un error al leer los datos.
     */
    List<Musico> cargar() throws Exception;

    /**
     * Metodo para guardar la lista de musicos en el repositorio o archivo
     * @param musicos
     * @throws Exception Lanza excepción si ocurre un error al escribir los datos.
     */
    void guardar(List<Musico> musicos) throws Exception;

    /**
     * Devuelve la ruta del archivo o repositorio que se está usando.
     * @return
     */
    String getRuta();
}
