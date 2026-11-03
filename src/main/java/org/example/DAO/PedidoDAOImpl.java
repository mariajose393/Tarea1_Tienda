package org.example.DAO;

import org.example.modeloPOJOs.Pedido;
import org.example.modeloPOJOs.Producto;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;



public class PedidoDAOImpl implements PedidoDAO{
    private final Connection connection;

    public PedidoDAOImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedidos (fecha_pedido, cantidad, id_producto) VALUES (?, ?, ?)";
        String sqlStock = "UPDATE productos SET stock = stock - ? WHERE id_producto = ? AND stock >= ?";

        try {
            connection.setAutoCommit(false);


            try (PreparedStatement stmtPedido = connection.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmtStock = connection.prepareStatement(sqlStock)) {

                stmtStock.setInt(1, pedido.getCantidad()); //indica la cantidad que debemos restar
                stmtStock.setInt(2, pedido.getIdProducto()); //id del producto que queremos modificar
                stmtStock.setInt(3, pedido.getCantidad()); //comprueba que hay stock suficiente

                int stockActualizado = stmtStock.executeUpdate();

                if (stockActualizado == 0) {
                    throw new SQLException("No existe el producto o no hay stock suficiente");
                }

                stmtPedido.setDate(1, Date.valueOf(pedido.getFechaPedido()));
                stmtPedido.setInt(2, pedido.getCantidad());
                stmtPedido.setInt(3, pedido.getIdProducto());

                stmtPedido.executeUpdate();

                try (ResultSet rs = stmtPedido.getGeneratedKeys()) {
                    if (rs.next()) {
                        pedido.setIdPedido(rs.getInt(1));
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                connection.setAutoCommit(true);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }



    @Override
    public List<Pedido> findAll() {
        List<Pedido> pedidos = new ArrayList<>();
            String sql = "SELECT p.id_pedido, p.fecha_pedido, p.cantidad, p.id_producto,pr.nombre AS nombre_producto FROM pedidos p" +
                    " LEFT JOIN productos pr ON  pr.id_producto = p.id_producto";  //nos aseguramos que obtenemos todos los pedidos aunque no tengan ningún producto
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(new Pedido(
                            rs.getInt("id_pedido"),
                            rs.getDate("fecha_pedido").toLocalDate(),
                            rs.getInt("cantidad"),
                            rs.getInt("id_producto"),
                            rs.getString("nombre_producto")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return pedidos;
    }

}



