import Model.Cliente;
import Model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase gestor para realizar metodos CRUD de las clases proporcionadas y derivados de ellas.
 * He añadido comentarios para facilitar la lectura del código.
 * @author delhoyoballestin23
 */
public class GestionBaseDatos {

    private static final String URL     = "jdbc:mysql://localhost:3306/examen-ud2-tienda";
    private static final String USER    = "root";
    private static final String PASS    = "";

    // ======================
    // CRUD CLIENTE
    // ======================

    /**
     * Metodo para listar clientes
     * @return lista de clientes
     * @throws SQLException
     */
    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> lista = new ArrayList<>(); // Lista auxiliar para mostrar clientes
        String sql = "SELECT * FROM cliente"; // Consulta SQL para seleccionar los clientes

        // Conexion con la BBDD
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) { // Puntero al siguiente cliente
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setEmail(rs.getString("email"));
                lista.add(c);
            }
        }
        return lista; // Lista a mostrar
    }

    /**
     * Metodo para actualizar el email de un cliente
     * @param idCliente parametro de busqueda
     * @param nuevoEmail parametro a actualizar
     * @throws SQLException
     */
    public void actualizarEmailCliente(int idCliente, String nuevoEmail) throws SQLException {
        String sql = "UPDATE cliente SET email = ? WHERE id = ?"; // Consulta SQL Para actualizar el email
        Connection con = null;
        PreparedStatement pst = null;

        try {
            // Conexion con la BBDD y desactivamos el autocommit
            con = DriverManager.getConnection(URL, USER, PASS);
            con.setAutoCommit(false); // Transacción manual

            // Realizar la consulta preparedStatement
            pst = con.prepareStatement(sql);
            pst.setString(1, nuevoEmail);
            pst.setInt(2, idCliente);

            // Realizar la consulta
            int filas = pst.executeUpdate();
            con.commit(); // Guardamos cambios
            // Muestra las filas modificadas si hubo exito
            if (filas > 0) System.out.println("Filas modificadas: " + filas);
            else System.out.println("No se ha encontrado el cliente con el ID: "+ idCliente);
        } catch (SQLException e) {
            try { // Cerramos los recursos
                if (con != null) con.rollback();
            } catch (Exception ex) {
                System.err.println("Error al deshacer cambios: " + ex.getMessage());
            }
        } finally {
            if (con != null) con.close();
            if (pst != null) pst.close();
        }
    }

    // ======================
    // CRUD PEDIDO
    // ======================

    /**
     * Metodo para insertar un pedido
     * @param p parametro a insertar
     * @throws SQLException
     */
    public void insertarPedido(Pedido p) throws SQLException {
        String sql = "INSERT INTO pedido (importe, id_cliente) VALUES (?, ?)"; // Consulta SQL para insertar un pedido

        // RETURN_GENERATED_KEYS para obtener el ID AUTOINCREMENTADO
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                //Realizar la consulta
                ps.setDouble(1, p.getImporte());
                ps.setInt(2, p.getIdCliente());

                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            p.setId(rs.getInt(1));
                        }
                    }
                }
            }
    }

    /**
     * Metodo para listar los pedidos de un cliente
     * @param idCliente parametro de busqueda
     * @return list de los pedidos del cliente
     * @throws SQLException
     */
    public List<Pedido> listarPedidosDeCliente(int idCliente) throws SQLException {
        List<Pedido> lista = new ArrayList<>(); // Lista auxiliar para mostrar los pedidos
        String sql = "SELECT * FROM pedido WHERE id_cliente = ?"; // Consulta SQL para seleccionar los pedidos

        // Conexion BBDD
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pedido p = new Pedido(
                            rs.getInt("id"),
                            rs.getDouble("importe"),
                            rs.getInt("id_cliente")
                    );
                    lista.add(p);
                }
            }
        }
        return lista;
    }

    /**
     * Metodo para borrar un pedido
     * @param idPedido parametro de busqueda
     * @throws SQLException
     */
    public void borrarPedido(int idPedido) throws SQLException {
        //String sql = "DELETE FROM pedido WHERE id = ?"; // Es el mismo
        String sql2 = "DELETE FROM pedido WHERE pedido.id = ?";

        // Conexion BBDD
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement(sql2)) {

            // Realizar consulta
            ps.setInt(1, idPedido);
            ps.executeUpdate();
        }
    }

    /**
     * Metodo para crear un cliente y sus pedidos
     * @param c parametro de busqueda
     * @param pedidos lista de pedidos
     * @throws SQLException
     */
    public void crearClienteConPedidos(Cliente c, List<Pedido> pedidos) throws SQLException {
        String sqlCliente = "INSERT INTO cliente (nombre, email) VALUES (?, ?)"; // Consulta SQL para insertar un cliente
        String sqlPedido = "INSERT INTO pedido (importe, id_cliente) VALUES (?, ?)"; // Consulta SQL para insertar un pedido

        Connection con = null;
        PreparedStatement psCliente = null;
        PreparedStatement psPedido = null;

        try {
            con = DriverManager.getConnection(URL, USER, PASS);

            // Desactivar autoCommit
            con.setAutoCommit(false);

            // Insertar cliente y recuperar ID AUTOINCREMENTADO
            psCliente = con.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
            psCliente.setString(1, c.getNombre());
            psCliente.setString(2, c.getEmail());
            psCliente.executeUpdate();

            // Obtener ID generado
            try (ResultSet rs = psCliente.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    c.setId(idGenerado); // Actualizar Cliente
                } else {
                    throw new SQLException("Error al obtener ID del cliente.");
                }
            }

            // Insertar todos los pedidos de ese ID (cliente)
            psPedido = con.prepareStatement(sqlPedido);
            for (Pedido p : pedidos) {
                psPedido.setDouble(1, p.getImporte());
                psPedido.setInt(2, c.getId()); // ID del cliente
                psPedido.executeUpdate();
            }

            // Guardar cambios (COMMIT) si va bien
            con.commit();
            //System.out.println("Cliente y pedidos creados correctamente.");

        } catch (SQLException e) {
            // Deshacer cambios (ROLLBACK) si hay error
            if (con != null) {
                System.err.println("Error. Deshaciendo cambios...");
                con.rollback();
            }
            throw e;
        } finally { // Liberar recursos
            if (psCliente != null) psCliente.close();
            if (psPedido != null) psPedido.close();
            if (con != null) {
                con.setAutoCommit(true); // Resetear autocommit
                con.close();
            }
        }
    }
}