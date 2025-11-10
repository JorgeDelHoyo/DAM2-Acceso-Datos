import java.sql.*;

public class EjemploConexion {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/tienda";
        String usuario = "root";
        String password = "";
        // Try-Catch con resources
        try(Connection con = DriverManager.getConnection(url,usuario,password)) {
            System.out.println("Conectado");

            // Preparo la sentencia que quuiero ejecutar
            String sqlQuery = "SELECT * FROM producto";
            Statement st = con.createStatement();
            // Ejecuto la sentencia SQL y recojo el resultado
            ResultSet resultado = st.executeQuery(sqlQuery);

            while (resultado.next()) {
                int id = resultado.getInt(1);
                String nombre = resultado.getString("nombre");
                double precio = resultado.getDouble("precio");
                String descripcion = resultado.getString("descripcion");
                System.out.println("ID: "+id+"; nombre: "+nombre+"; precio: "+precio+"; descripcion: "+descripcion);
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexión: "+e.getMessage());
            System.out.println("Codigo error: "+e.getErrorCode());
            System.out.println("Estado SQL: "+e.getSQLState());
        }
    }
}
