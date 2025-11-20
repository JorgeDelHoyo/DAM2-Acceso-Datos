package service;

import Interfaces.MusicoRepositorio;
import Objetos.Musico;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MusicoService {
    private final MusicoRepositorio musicoRepositorio;

    /**
     * Constructor que recibe la implementación del repositorio a utilizar.
     * @param musicoRepositorio una implementación de MusicoRepositorio (JDBC, Fichero, etc.).
     */
    public MusicoService(MusicoRepositorio musicoRepositorio) {
        this.musicoRepositorio = musicoRepositorio;
    }

    public List<Musico> obtenerTodosLosMusicos() throws SQLException {
        return musicoRepositorio.obtenerTodos();
    }

    public Optional<Musico> obtenerMusicoPorId(int id) throws SQLException {
        return musicoRepositorio.obtenerPorId(id);
    }

    public void guardarMusico(Musico musico) throws SQLException {
        if (musico.getNombre() == null || musico.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del músico no puede estar vacío");
        }
        musicoRepositorio.guardar(musico);
    }

    public void actualizarMusico(Musico musico) throws SQLException {
        if (musico.getIdMusico() <= 0) {
            throw new IllegalArgumentException("El ID del músico no es válido");
        }
        musicoRepositorio.actualizar(musico);
    }

    public void eliminarMusico(int id) throws SQLException {
        musicoRepositorio.eliminar(id);
    }

    public List<Musico> buscarMusicosPorBanda(int idBanda) throws SQLException {
        return musicoRepositorio.buscarPorBanda(idBanda);
    }
}
