import Model.Cliente;
import Model.Pedido;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * !!!!!!!!! IMPORTANTE !!!!!!!!!
 * CAMBIAR ID metodo BORRAR PEDIDO
 * @author delhoyoballestin23
 */
public class Main {

    public static void main(String[] args) {

        GestionBaseDatos gbd = new GestionBaseDatos();

        try {
            // 1. Listar clientes
            System.out.println("=== Crear la BBDD ===");
            gbd.listarClientes().forEach(System.out::println);

            // 2. ACTUALIZAR EMAIL CLIENTE
            gbd.actualizarEmailCliente(1, "juan.nuevo@example.com");
            System.out.println("\n=== Actualizar email de Juan ===");
            gbd.listarClientes().forEach(System.out::println);

            // 3. INSERTAR PEDIDOS PARA c1
            Pedido p1 = new Pedido(0, 100.0, 1);
            Pedido p2 = new Pedido(0, 50.0,  1);
            gbd.insertarPedido(p1);
            gbd.insertarPedido(p2);
            System.out.println("\n=== Insertar pedidos para Juan (c1) ===");
            System.out.println("Pedidos del cliente " + 1 + ":");
            gbd.listarPedidosDeCliente(1).forEach(System.out::println);

            // 4. Crear Cliente Con Pedidos
            Cliente c3 = new Cliente(0, "Cliente", "transaccion@example.com");
            List<Pedido> pedidos = Arrays.asList(
                    new Pedido(0, 200.0, 0),
                     new Pedido(0, 300.0, 0)
            );
            gbd.crearClienteConPedidos(c3, pedidos);
            System.out.println("\n=== Crear cliente con pedidos ===");
            gbd.listarClientes().forEach(System.out::println);
            System.out.println("Pedidos del cliente (id " + c3.getId() + "):");
            gbd.listarPedidosDeCliente(c3.getId()).forEach(System.out::println);

            // 5. BORRAR UN PEDIDO
            gbd.borrarPedido(2); // He cambiado el ID para que funcione el metodo
            System.out.println("\n=== Borrar un pedido de Juan ===");
            System.out.println("Pedidos del cliente " + 1 + " tras borrar un pedido:");
            gbd.listarPedidosDeCliente(1).forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
