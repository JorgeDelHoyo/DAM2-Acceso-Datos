import java.sql.*;

/**
 * ============================= Ejercicio 6: Simular una SQL-Injection =============================
 * Conectar a la base de datos
 * Escribir esta consulta: SELECT * FROM usuarios WHERE email=”....” and contrasena= “...”
 * Si devuelve al menos un resultado entendemos que existe el usuario y conocemos su contraseña.
 * Si devuelve 0 resultados el usuario o contraseña no existen
 * Intenta escribir una inyección de SQL para que nos devuelva al menos un resultado simulando que hemos entrado en el sistema sin conocer la contraseña.
 * Otra opción es intentar borrar la tabla de usuarios a través de una inyección SQL
 */
public class Ejercicio06 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mi_base_de_datos";
        String user = "root";
        String pass = "";

        try(Connection con = DriverManager.getConnection(url,user,pass)){

            // Usuario y contraseña extraido de la BBDD
            String nombreUsuario = "usuario1@ejemplo.com";
            String passwordUsuario = "123456";
            String passWordInjection = "12345' OR '1'='1";
            String passWordInjection2 = "12345'; DELETE FROM usuarios where '1'='1";

            String sqlQuery = "SELECT * FROM usuarios WHERE email = '"+nombreUsuario+"' and contrasena = '"+passWordInjection+"' ";

            // Ejecuto y recojo resultados
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);

            // Si hay resultado significa que he acertado
            if(rs.next()){
                System.out.println("Usuario encontrado");
            }else{
                System.out.println("Usuario y/o contraseña incorrectos");
            }

            st.close();
        }catch (SQLException e) {
            System.out.println("Error en la conexión o la consulta SQL:");
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("Código error: " + e.getErrorCode());
            System.out.println("Estado SQL: " + e.getSQLState());
        }
    }
}
