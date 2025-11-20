package repository;

import Interfaces.BandaRepositorio;
import Objetos.Banda;
import util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BandaRepositorioJDBC implements BandaRepositorio {

    private Connection obtenerConexion() throws SQLException {
        return DatabaseManager.getConexion();
    }

    @Override
    public List<Banda> obtenerTodos() throws SQLException {
        List<Banda> bandas = new ArrayList<>();
        String sql = "SELECT * FROM banda";
        try (Connection conn = obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bandas.add(crearBandaDesdeResultSet(rs));
            }
        }
        return bandas;
    }

    @Override
    public Optional<Banda> obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM banda WHERE id_banda = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(crearBandaDesdeResultSet(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void guardar(Banda banda) throws SQLException {
        String sql = "INSERT INTO banda (nombre_banda) VALUES (?)";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, banda.getNombreBanda());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    banda.setIdBanda(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void actualizar(Banda banda) throws SQLException {
        String sql = "UPDATE banda SET nombre_banda = ? WHERE id_banda = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, banda.getNombreBanda());
            stmt.setInt(2, banda.getIdBanda());
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM banda WHERE id_banda = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Banda crearBandaDesdeResultSet(ResultSet rs) throws SQLException {
        return new Banda(
                rs.getInt("id_banda"),
                rs.getString("nombre_banda")
        );
    }
}
