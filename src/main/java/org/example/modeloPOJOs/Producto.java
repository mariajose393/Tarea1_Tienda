package org.example.modeloPOJOs;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class Producto {
    private Integer idProducto;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;
}
