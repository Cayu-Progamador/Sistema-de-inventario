package taller2backend.proyect.dto;

import java.util.List;

import lombok.Data;

@Data
public class VentaRequestDto {
    private Long idEmpleado;
    private Long idMetodoPago;
    private Double total;
    private Double subtotal;
    private Double totalDescuento;
    private List<DetalleVentaDTO> detalles;
    //factura
    private Long idCliente;
    private String tipoDocumento;//factura o comprobante
}
