package org.example.modeloPOJOs;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Pedido {
    private Integer idPedido;
    private LocalDate fechaPedido;
    private Integer cantidad;
    private Integer idProducto;
    private String nombreProducto;
}
