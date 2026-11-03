package org.example.DAO;


import org.example.modeloPOJOs.Producto;

import java.util.List;

public interface ProductoDAO {
    void create (Producto entity);
    Producto findById(Integer idProducto);
    void update(Integer idProducto, Integer stock);


}
