package Interfaces;

import Objetos.Musico;
import java.sql.SQLException;
import java.util.List;

public interface MusicoRepositorio extends Repositorio<Musico> {
    List<Musico> buscarPorBanda(int idBanda) throws SQLException;
}
