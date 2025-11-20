package repository;

import Interfaces.MusicoRepositorio;
import Objetos.Banda;
import Objetos.Musico;
import util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MusicoRepositorioJDBC implements MusicoRepositorio {

    private Connection obtenerConexion() throws SQLException {
        return DatabaseManager.getConexion();
    }

    @Override
    public List<Musico> obtenerTodos() throws SQLException {
        List<Musico> musicos = new ArrayList<>();
        String sql = "SELECT * FROM musico";
        try (Connection conn = obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                musicos.add(crearMusicoDesdeResultSet(rs));
            }
        }
        return musicos;
    }

    @Override
    public Optional<Musico> obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM musico WHERE id_musico = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(crearMusicoDesdeResultSet(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void guardar(Musico musico) throws SQLException {
        String sql = "INSERT INTO musico (nombre, instrumento) VALUES (?, ?)";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, musico.getNombre());
            stmt.setString(2, musico.getInstrumento());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    musico.setIdMusico(generatedKeys.getInt(1));
                }
            }
            guardarBandasDeMusico(musico);
        }
    }

    @Override
    public void actualizar(Musico musico) throws SQLException {
        String sql = "UPDATE musico SET nombre = ?, instrumento = ? WHERE id_musico = ?";
        try (Connection conn = obtenerConexion()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, musico.getNombre());
                stmt.setString(2, musico.getInstrumento());
                stmt.setInt(3, musico.getIdMusico());
                stmt.executeUpdate();
            }
            // Actualizar bandas
            eliminarBandasDeMusico(musico.getIdMusico());
            guardarBandasDeMusico(musico);
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM musico WHERE id_musico = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Musico> buscarPorBanda(int idBanda) throws SQLException {
        List<Musico> musicos = new ArrayList<>();
        String sql = "SELECT m.* FROM musico m " +
                     "JOIN musico_banda mb ON m.id_musico = mb.id_musico " +
                     "WHERE mb.id_banda = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idBanda);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    musicos.add(crearMusicoDesdeResultSet(rs));
                }
            }
        }
        return musicos;
    }

    private Musico crearMusicoDesdeResultSet(ResultSet rs) throws SQLException {
        Musico musico = new Musico(
                rs.getInt("id_musico"),
                rs.getString("nombre"),
                rs.getString("instrumento")
        );
        musico.setBandas(obtenerBandasDeMusico(musico.getIdMusico()));
        return musico;
    }

    private List<Banda> obtenerBandasDeMusico(int idMusico) throws SQLException {
        List<Banda> bandas = new ArrayList<>();
        String sql = "SELECT b.* FROM banda b " +
                     "JOIN musico_banda mb ON b.id_banda = mb.id_banda " +
                     "WHERE mb.id_musico = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMusico);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bandas.add(new Banda(rs.getInt("id_banda"), rs.getString("nombre_banda")));
                }
            }
        }
        return bandas;
    }

    private void guardarBandasDeMusico(Musico musico) throws SQLException {
        String sql = "INSERT INTO musico_banda (id_musico, id_banda) VALUES (?, ?)";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Banda banda : musico.getBandas()) {
                stmt.setInt(1, musico.getIdMusico());
                stmt.setInt(2, banda.getIdBanda());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private void eliminarBandasDeMusico(int idMusico) throws SQLException {
        String sql = "DELETE FROM musico_banda WHERE id_musico = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMusico);
            stmt.executeUpdate();
        }
    }
}
