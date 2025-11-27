package taller2backend.proyect.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaListadoDto {
    private Long idVenta;
    private Date fechaVenta;
    private String numeroVenta;
    private Double totalventa;
    private Double subtotal;
    private Double totalDescuento;
    private String estado;
    private String nombreEmpleado;
}
