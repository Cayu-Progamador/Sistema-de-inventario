package taller2backend.proyect.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistorialProductoDTO {
    // Datos de la Venta
    private Long idVenta;
    private Date fechaVenta;
    private String numeroVenta;

    // Datos del Pago
    private String nombrePago;
    // Datos del Producto
    private String nombreProducto;   // Coincide con Producto.java
    private Integer cantidad;        // Coincide con DetalleVenta.java
    private Double precioUnitario;  // Coincide con DetalleVenta.java
    private Double subtotalProducto; // Calculado
    
}
