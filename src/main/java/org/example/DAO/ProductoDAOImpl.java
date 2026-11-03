package org.example.DAO;

import org.example.modeloPOJOs.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;




public class ProductoDAOImpl implements ProductoDAO{

    private final Connection connection;
    public ProductoDAOImpl(Connection connection) {this.connection = connection;}

    @Override
    public void create(Producto producto) {
        String sql = "INSERT INTO productos (nombre, precio, stock) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, producto.getNombre());
            stmt.setBigDecimal(2, producto.getPrecio());
            stmt.setInt(3, producto.getStock());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    producto.setIdProducto(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Producto findById(Integer idProducto) {
        String sql = "SELECT * FROM productos WHERE id_producto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getBigDecimal("precio"),
                            rs.getInt("stock")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void update(Integer idProducto, Integer stock) {
        String sql = "UPDATE productos SET stock = ? WHERE id_producto= ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, stock);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




}
