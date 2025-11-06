import java.sql.*;

public class Main2 {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/tienda";
        String user = "root";
        String password = "";

        try {
            // tienda es la base de datos a la que me conecto, root el usuario y password la contraseña
            Connection con = DriverManager.getConnection(url,user,password);

            // Preparo la consulta SQL que quiero ejecutar
            Statement st = con.createStatement();

            // La ejecuto y recojo el resutlado
            ResultSet rs = st.executeQuery("SELECT id, nombre, precio, descripcion FROM producto");

            // Itero sobre los resultados recibidos
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                String descripcion = rs.getString("descripcion");
                System.out.println("ID: "+id+"; nombre: "+nombre+"; precio: "+precio+"; descripcion: "+descripcion);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}