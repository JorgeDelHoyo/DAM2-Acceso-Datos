package Interfaces;

import Objetos.Musico;

import java.io.IOException;
import java.util.List;

public interface MusicoRepository {
    void guardarMusico(Musico m) throws IOException;

    Musico findByID(int idMusico);

    List<Musico> findAll() throws IOException;
}
