package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/musica";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";
    private static Connection conexion;

    private DatabaseManager() {
    }

    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                // Cargar el driver de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establecer la conexión
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Error al cargar el driver de MySQL", e);
            }
        }
        return conexion;
    }

    public static void cerrarConexion() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
            conexion = null;
        }
    }

    public static void iniciarTransaccion() throws SQLException {
        getConexion().setAutoCommit(false);
    }

    public static void confirmarTransaccion() throws SQLException {
        getConexion().commit();
        getConexion().setAutoCommit(true);
    }

    public static void revertirTransaccion() throws SQLException {
        getConexion().rollback();
        getConexion().setAutoCommit(true);
    }
}
