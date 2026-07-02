package proyectobackend.proyect.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaDetalleDto {
    private String nombreProducto;
    private int cantidad;
    private Double precioUnitario;
    private Double subtotal;
}
