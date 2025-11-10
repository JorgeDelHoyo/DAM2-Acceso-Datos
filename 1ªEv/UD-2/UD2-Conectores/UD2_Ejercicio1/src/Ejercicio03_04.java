import java.sql.*;

/**
 * ============================= Ejercicio 3: SELECT y ResultSet =============================
 * Ejecutar la consulta SQL en PHPMyAdmin
 * Crea una conexión a la base de datos tiempo
 * Ejecuta la consulta "SELECT * FROM producto" utilizando la función executeQuery().
 * * (Aunque ya hemos comentado en clase que el * es mala practica)
 * Recoge el resultado de ejecución de la consulta en un ResultSet, recorrerlo e imprime el resultado por pantalla.
 * Recoge las excepciones para comprobar error de conexión, o error en la consulta SQL. Prueba ambos.
 *
 * ============================= Ejercicio 4: Insertar, modificar y borrar datos =============================
 * Amplía el ejercicio anterior con las siguientes operaciones.
 * * Insertar un nuevo producto desde Java.
 * * Modificar su precio.
 * * Eliminar otro.
 * * Mostrar la tabla actualizada.
 */
public class Ejercicio03_04 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/tienda";
        String user = "root";
        String password = "";

        try (Connection conexion = DriverManager.getConnection(url,user,password)){

            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM producto");

            while(rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                String descripcion = rs.getString("descripcion");
            }

            // ===================== EJERCICIO 4 =====================
            // Insertar un nuevo producto
            System.out.println("\n=== INSERTANDO NUEVO PRODUCTO ===");
            String insertar = "INSERT INTO producto (nombre, precio, descripcion) VALUES ('Kinder sorpresa', 2.70, 'Tiene sorpresa dentro')";
            int filasInsertadas = st.executeUpdate(insertar);
            System.out.println("Filas insertadas: " + filasInsertadas);

            // Modificar un producto
            System.out.println("\n=== MODIFICANDO PRECIO ===");
            String modificar = "UPDATE producto SET precio = 2.80 WHERE nombre = 'Kinder sorpresa'";
            int filasModificadas = st.executeUpdate(modificar);
            System.out.println("Filas modificadas: " + filasModificadas);

            // Eliminar un producto
            System.out.println("\n=== ELIMINANDO PRODUCTO ===");
            String eliminar = "DELETE FROM producto WHERE nombre = 'Kinder sorpresa'";
            int filasEliminadas = st.executeUpdate(eliminar);
            System.out.println("Filas eliminadas: " + filasEliminadas);

            // Mostrar la tabla
            System.out.println("\n=== TABLA ACTUALIZADA ===");
            while(rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                String descripcion = rs.getString("descripcion");
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexión o la consulta SQL:");
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("Código error: " + e.getErrorCode());
            System.out.println("Estado SQL: " + e.getSQLState());
        }
    }
}