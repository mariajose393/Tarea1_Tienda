package org.example.DAO;

import org.example.modeloPOJOs.Pedido;

import java.util.List;

public interface PedidoDAO {
    void create (Pedido entity);
    List<Pedido> findAll();

}
