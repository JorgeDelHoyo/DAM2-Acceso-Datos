import java.sql.*;

/**
 * BASE DE DATOS EN XAMPP --> http://localhost/phpmyadmin/index.php?route=/sql&pos=0&db=accesodatos&table=alimentos
 */
public class Main {
    public static void main(String[] args) {
        //Establezco conexion con la BBDD
        Connection conexion = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/accesodatos",
                    "root", "");

        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        String sentenciaSql = "SELECT id, nombre, calorias, valor_energetico FROM alimentos WHERE calorias > ?";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);

            // Por temas de seguridad hay que hacerlo asi y te aseguras del valor que introduces
            // Doy valor al "?" de la sentenciasql el valor de 100
            sentencia.setInt(1,100);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                System.out.println("\nid: "+resultado.getInt(1));
                System.out.println("nombre: " + resultado.getString(2));
                System.out.println("calorias: " + resultado.getInt(3));
                System.out.println("valor_energetico: " + resultado.getFloat(4));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                    resultado.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }

        try {
            conexion.close();
            conexion = null;
            System.out.println("\n======== Desconectando ========");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }
}