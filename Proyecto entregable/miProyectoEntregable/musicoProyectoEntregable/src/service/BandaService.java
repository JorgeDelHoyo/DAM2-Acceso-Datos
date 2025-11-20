package service;

import Interfaces.BandaRepositorio;
import Objetos.Banda;
import repository.BandaRepositorioJDBC;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BandaService {
    private final BandaRepositorio bandaRepositorio;

    public BandaService() {
        this.bandaRepositorio = new BandaRepositorioJDBC();
    }

    public List<Banda> obtenerTodasLasBandas() throws SQLException {
        return bandaRepositorio.obtenerTodos();
    }

    public Optional<Banda> obtenerBandaPorId(int id) throws SQLException {
        return bandaRepositorio.obtenerPorId(id);
    }

    public void guardarBanda(Banda banda) throws SQLException {
        // Aquí se podría añadir lógica de negocio, como validaciones
        if (banda.getNombreBanda() == null || banda.getNombreBanda().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la banda no puede estar vacío");
        }
        bandaRepositorio.guardar(banda);
    }

    public void actualizarBanda(Banda banda) throws SQLException {
        if (banda.getIdBanda() <= 0) {
            throw new IllegalArgumentException("El ID de la banda no es válido");
        }
        bandaRepositorio.actualizar(banda);
    }

    public void eliminarBanda(int id) throws SQLException {
        bandaRepositorio.eliminar(id);
    }
}
