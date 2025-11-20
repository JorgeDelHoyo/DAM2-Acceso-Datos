package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupDatabase {

    public static void inicializar() {
        try (Connection conn = DatabaseManager.getConexion();
             Statement stmt = conn.createStatement()) {

            // Crear base de datos si no existe
            stmt.execute("CREATE DATABASE IF NOT EXISTS musica");
            stmt.execute("USE musica");

            // Crear tabla banda
            stmt.execute("CREATE TABLE IF NOT EXISTS banda (" +
                         "id_banda INT AUTO_INCREMENT PRIMARY KEY," +
                         "nombre_banda VARCHAR(255) NOT NULL" +
                         ")");

            // Crear tabla musico
            stmt.execute("CREATE TABLE IF NOT EXISTS musico (" +
                         "id_musico INT AUTO_INCREMENT PRIMARY KEY," +
                         "nombre VARCHAR(255) NOT NULL," +
                         "instrumento VARCHAR(255)," +
                         "foto BLOB" + // Columna para la imagen
                         ")");

            // Crear tabla de unión musico_banda
            stmt.execute("CREATE TABLE IF NOT EXISTS musico_banda (" +
                         "id_musico INT," +
                         "id_banda INT," +
                         "PRIMARY KEY (id_musico, id_banda)," +
                         "FOREIGN KEY (id_musico) REFERENCES musico(id_musico) ON DELETE CASCADE," +
                         "FOREIGN KEY (id_banda) REFERENCES banda(id_banda) ON DELETE CASCADE" +
                         ")");

            System.out.println("Base de datos y tablas inicializadas correctamente.");
            precargarDatos();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al inicializar la base de datos", e);
        }
    }

    private static void precargarDatos() {
        try {
            DatabaseManager.iniciarTransaccion();

            try (Connection conn = DatabaseManager.getConexion();
                 Statement stmt = conn.createStatement()) {

                // Precargar bandas
                stmt.executeUpdate("INSERT INTO banda (nombre_banda) VALUES ('The Beatles') ON DUPLICATE KEY UPDATE nombre_banda=nombre_banda;");
                stmt.executeUpdate("INSERT INTO banda (nombre_banda) VALUES ('Queen') ON DUPLICATE KEY UPDATE nombre_banda=nombre_banda;");

                // Precargar musicos
                stmt.executeUpdate("INSERT INTO musico (nombre, instrumento) VALUES ('John Lennon', 'Guitarra') ON DUPLICATE KEY UPDATE nombre=nombre;");
                stmt.executeUpdate("INSERT INTO musico (nombre, instrumento) VALUES ('Freddie Mercury', 'Voz') ON DUPLICATE KEY UPDATE nombre=nombre;");

                // Precargar relación musico_banda
                stmt.executeUpdate("INSERT INTO musico_banda (id_musico, id_banda) VALUES (1, 1) ON DUPLICATE KEY UPDATE id_musico=id_musico;");
                stmt.executeUpdate("INSERT INTO musico_banda (id_musico, id_banda) VALUES (2, 2) ON DUPLICATE KEY UPDATE id_musico=id_musico;");
            }

            DatabaseManager.confirmarTransaccion();
            System.out.println("Datos precargados correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DatabaseManager.revertirTransaccion();
                System.err.println("Error en la precarga de datos. Se revirtió la transacción.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
