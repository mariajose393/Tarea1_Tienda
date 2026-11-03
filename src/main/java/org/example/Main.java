package org.example;

import org.example.DAO.PedidoDAO;
import org.example.DAO.PedidoDAOImpl;
import org.example.DAO.ProductoDAOImpl;
import org.example.modeloPOJOs.Pedido;
import org.example.modeloPOJOs.Producto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String URL = "jdbc:mariadb://localhost:3306/tiendadb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PedidoDAOImpl pedidoDAO = new PedidoDAOImpl(connection);
            ProductoDAOImpl productoDAO = new ProductoDAOImpl(connection);
            Scanner scanner = new Scanner(System.in);


            int opcion;

            do {
                System.out.println("--------------MENÚ PRINCIPAL---------------");
                System.out.println("1. Insertar producto");
                System.out.println("2. Consultar producto por ID");
                System.out.println("3. Actualizar stock de producto");
                System.out.println("4. Crear pedido");
                System.out.println("5. Listado de pedidos");
                System.out.println("6. Salir");
                System.out.print("Seleccione una opción: (1-6)");

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.println("~~~~Insertar producto~~~~");
                        System.out.print("\nIntroduzca el nombre del producto: ");
                        String nombre = scanner.nextLine();

                        System.out.print("\nIntroduzca el precio: ");
                        BigDecimal precio = scanner.nextBigDecimal();

                        System.out.print("\nIntroduzca el stock: ");
                        int stock = scanner.nextInt();
                        scanner.nextLine();

                        Producto producto = new Producto();
                        producto.setNombre(nombre);
                        producto.setPrecio(precio);
                        producto.setStock(stock);

                        productoDAO.create(producto);

                        System.out.println("\nEl productose ha insertado correctamente.");

                        break;

                    case 2:
                        System.out.println("~~~~Consultar producto por id~~~~");
                        System.out.print("Introduzca el ID del producto: ");
                        int idProducto = scanner.nextInt();

                        Producto productoEncontrado = productoDAO.findById(idProducto);

                        if (productoEncontrado != null) {
                            System.out.println("\nProducto encontrado:");
                            System.out.println("ID: " + productoEncontrado.getIdProducto());
                            System.out.println("Nombre: " + productoEncontrado.getNombre());
                            System.out.println("Precio: " + productoEncontrado.getPrecio());
                            System.out.println("Stock: " + productoEncontrado.getStock());
                        } else {
                            System.out.println("No existe ningún producto con ese ID.");
                        }
                        break;

                    case 3:
                        System.out.println("~~~~Actualizar stock de producto~~~~");
                        System.out.println("Introduzca el ID del producto:");
                        int idActStock = scanner.nextInt();
                        Producto productoActStock = productoDAO.findById(idActStock);
                        if (productoActStock != null){
                            System.out.println("Producto: " + productoActStock.getNombre()+", Stock actual: "+productoActStock.getStock());
                            System.out.println("\n Introduzca el nuevo stock de "+productoActStock.getNombre()+" : ");
                            int nuevoStock = scanner.nextInt();

                            productoDAO.update(idActStock, nuevoStock);
                            System.out.println("\n El Stock de "+ productoActStock.getNombre()+ " se ha actualizado a "+ nuevoStock);
                        } else {
                            System.out.println("No existe ningún producto con ese ID.");
                        }
                        break;

                    case 4:
                        System.out.println("~~~~Crear pedido~~~~");
                        System.out.print("Introduzca el ID del producto: ");
                        int idProductoPedido = scanner.nextInt();

                        Producto productoPedido = productoDAO.findById(idProductoPedido);

                        if (productoPedido != null) {

                            System.out.println("Producto: " + productoPedido.getNombre()+", Stock disponible: " + productoPedido.getStock() );
                            System.out.print("Introduzca la cantidad que desea de " + productoPedido.getNombre()+ ": ");
                            int cantidad = scanner.nextInt();

                            if (cantidad > 0 && cantidad <= productoPedido.getStock()) {


                                LocalDate fechaPedido = LocalDate.now();

                                Pedido pedido = new Pedido();
                                pedido.setFechaPedido(fechaPedido);
                                pedido.setCantidad(cantidad);
                                pedido.setIdProducto(idProductoPedido);

                                pedidoDAO.create(pedido);

                                System.out.println("\nEl pedido se ha creado correctamente.");

                            } else {
                                System.out.println("No hay suficiente stock. La cantidad debe ser menor de "+ productoPedido.getStock());
                            }

                        } else {
                            System.out.println("No existe ningún producto con ese ID.");
                        }

                        break;

                    case 5:
                        System.out.println("~~~~Listado de pedidos~~~~");
                        List <Pedido> pedidos = pedidoDAO.findAll();
                        if (!pedidos.isEmpty()) {
                            for (Pedido pedido : pedidos) {
                                System.out.println("\nID pedido: " + pedido.getIdPedido()+", Fecha: "+pedido.getFechaPedido()
                                        +", ID producto: "+pedido.getIdProducto()+ ", Nombre Producto: " + pedido.getNombreProducto()
                                        +", Cantidad: "+ pedido.getCantidad());

                            }
                        } else {
                            System.out.println("No se ha creado ningún pedido.");
                        }
                        break;

                    case 6:
                        System.out.println("Saliendo de la aplicación...¡Hasta Pronto!");
                        break;

                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }

            } while (opcion != 6);

            scanner.close();

        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }






    }
}