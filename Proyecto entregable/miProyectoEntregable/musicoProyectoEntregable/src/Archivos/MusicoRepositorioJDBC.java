package Archivos;

import Interfaces.MusicoRepositorio;
import Objetos.Banda;
import Objetos.Musico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MusicoRepositorioJDBC implements MusicoRepositorio {

    private final String url;
    private final String user;
    private final String password;
    private final String dbName;

    public MusicoRepositorioJDBC(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.dbName = url.substring(url.lastIndexOf("/") + 1);
        
        inicializarBaseDeDatos();
        inicializarTablas();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private void inicializarBaseDeDatos() {
        String serverUrl = url.substring(0, url.lastIndexOf("/"));
        try (Connection conn = DriverManager.getConnection(serverUrl, user, password);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE DATABASE IF NOT EXISTS " + this.dbName);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo conectar o crear la base de datos.", e);
        }
    }

    private void inicializarTablas() {
        String sqlMusicos = "CREATE TABLE IF NOT EXISTS musicos (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nombre VARCHAR(255) NOT NULL," +
                "instrumento VARCHAR(255) NOT NULL" +
                ");";
        String sqlBandas = "CREATE TABLE IF NOT EXISTS bandas (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nombre VARCHAR(255) NOT NULL UNIQUE" +
                ");";
        String sqlMusicoBanda = "CREATE TABLE IF NOT EXISTS musico_banda (" +
                "musico_id INT NOT NULL," +
                "banda_id INT NOT NULL," +
                "PRIMARY KEY (musico_id, banda_id)," +
                "FOREIGN KEY (musico_id) REFERENCES musicos(id) ON DELETE CASCADE," +
                "FOREIGN KEY (banda_id) REFERENCES bandas(id) ON DELETE CASCADE" +
                ");";
        
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sqlMusicos);
            stmt.execute(sqlBandas);
            stmt.execute(sqlMusicoBanda);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudieron crear las tablas.", e);
        }
    }

    @Override
    public Musico añadir(Musico musico) throws SQLException {
        String sql = "INSERT INTO musicos (nombre, instrumento) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            conn.setAutoCommit(false); // Iniciar transacción

            pstmt.setString(1, musico.getNombre());
            pstmt.setString(2, musico.getInstrumento());
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    musico.setIdMusico(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Fallo al obtener el ID del músico.");
                }
            }
            
            // Gestionar bandas
            actualizarBandasDeMusico(conn, musico);

            conn.commit(); // Finalizar transacción
            return musico;
        }
    }

    @Override
    public List<Musico> cargar() throws SQLException {
        List<Musico> musicos = new ArrayList<>();
        String sql = "SELECT * FROM musicos";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Musico musico = new Musico(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("instrumento")
                );
                musico.setBandas(cargarBandasDeMusico(conn, musico.getIdMusico()));
                musicos.add(musico);
            }
        }
        return musicos;
    }

    @Override
    public Optional<Musico> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM musicos WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Musico musico = new Musico(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("instrumento")
                    );
                    musico.setBandas(cargarBandasDeMusico(conn, musico.getIdMusico()));
                    return Optional.of(musico);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean modificar(Musico musico) throws SQLException {
        String sql = "UPDATE musicos SET nombre = ?, instrumento = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            conn.setAutoCommit(false);

            pstmt.setString(1, musico.getNombre());
            pstmt.setString(2, musico.getInstrumento());
            pstmt.setInt(3, musico.getIdMusico());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                actualizarBandasDeMusico(conn, musico);
            }
            
            conn.commit();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM musicos WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    private List<Banda> cargarBandasDeMusico(Connection conn, int musicoId) throws SQLException {
        List<Banda> bandas = new ArrayList<>();
        String sql = "SELECT b.id, b.nombre FROM bandas b " +
                     "JOIN musico_banda mb ON b.id = mb.banda_id " +
                     "WHERE mb.musico_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, musicoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bandas.add(new Banda(rs.getInt("id"), rs.getString("nombre")));
                }
            }
        }
        return bandas;
    }

    private void actualizarBandasDeMusico(Connection conn, Musico musico) throws SQLException {
        // 1. Borrar relaciones existentes
        String sqlDelete = "DELETE FROM musico_banda WHERE musico_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            pstmt.setInt(1, musico.getIdMusico());
            pstmt.executeUpdate();
        }

        // 2. Insertar nuevas relaciones
        String sqlInsertBanda = "INSERT INTO bandas (nombre) VALUES (?) ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id)";
        String sqlGetBandaId = "SELECT id FROM bandas WHERE nombre = ?";
        String sqlInsertRelacion = "INSERT INTO musico_banda (musico_id, banda_id) VALUES (?, ?)";

        for (Banda banda : musico.getBandas()) {
            // Asegurarse de que la banda existe y obtener su ID
            int bandaId;
            try (PreparedStatement pstmt = conn.prepareStatement(sqlGetBandaId)) {
                pstmt.setString(1, banda.getNombreBanda());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    bandaId = rs.getInt("id");
                } else {
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsertBanda, Statement.RETURN_GENERATED_KEYS)) {
                        pstmtInsert.setString(1, banda.getNombreBanda());
                        pstmtInsert.executeUpdate();
                        ResultSet generatedKeys = pstmtInsert.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            bandaId = generatedKeys.getInt(1);
                        } else {
                            throw new SQLException("No se pudo crear la banda " + banda.getNombreBanda());
                        }
                    }
                }
            }
            
            // Insertar la relación
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsertRelacion)) {
                pstmt.setInt(1, musico.getIdMusico());
                pstmt.setInt(2, bandaId);
                pstmt.executeUpdate();
            }
        }
    }

    @Override
    public void guardar(List<Musico> musicos) throws Exception {
        // Implementación transaccional para guardar una lista completa
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            
            // Borrar todo para empezar de cero
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DELETE FROM musico_banda");
                stmt.execute("DELETE FROM musicos");
                stmt.execute("DELETE FROM bandas");
            }

            for (Musico musico : musicos) {
                // Re-usamos la lógica de añadir, pero dentro de nuestra transacción
                añadir(musico); 
            }
            
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public String getRuta() {
        return this.url;
    }
}
