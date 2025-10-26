package Interfaces;

import Objetos.Musico;

import java.io.IOException;
import java.util.List;

public interface MusicoRepositorio {
    List<Musico> cargar() throws Exception;
    // Metodo que debe implementar cualquier clase que use musicos.
    // Devuelve la lista de musicos guardada en el archivo o repositorio.
    // Lanza excepción si ocurre un error al leer los datos.

    void guardar(List<Musico> musicos) throws Exception;
    // Metodo para guardar la lista de musicos en el repositorio o archivo.
    // Lanza excepción si ocurre un error al escribir los datos.

    String getRuta();
    // Devuelve la ruta del archivo o repositorio que se está usando.
}
