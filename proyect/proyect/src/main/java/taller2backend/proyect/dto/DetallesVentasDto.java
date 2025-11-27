package taller2backend.proyect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallesVentasDto {

    private String producto;       // Nombre del producto
    private Double precioUnitario; // Precio unitario
    private Integer cantidad;      // Cantidad vendida
    private Double subtotal;       // precioUnitario * cantidad
}
