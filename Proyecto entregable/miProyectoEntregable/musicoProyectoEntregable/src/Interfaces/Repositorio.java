package Interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repositorio<T> {
    List<T> obtenerTodos() throws SQLException;
    Optional<T> obtenerPorId(int id) throws SQLException;
    void guardar(T t) throws SQLException;
    void actualizar(T t) throws SQLException;
    void eliminar(int id) throws SQLException;
}
