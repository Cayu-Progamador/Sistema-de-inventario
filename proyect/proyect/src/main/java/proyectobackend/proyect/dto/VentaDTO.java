package proyectobackend.proyect.dto;

import java.util.Date;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class VentaDTO {
    private Long idVenta; // El ID de la venta a la que perteneció
    private Date fechaVenta;
    private String nombreProducto;
    private int cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private String nombreEmpleado; //La lista de productos
}
