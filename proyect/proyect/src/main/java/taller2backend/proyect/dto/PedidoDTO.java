package taller2backend.proyect.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Long idPedido;
    private Long idProveedor;
    private Date fechaPedido;
    private Date fechaEntrega;
    private Double totalPedido;
    private String estado;
    private List<DetallePedidoDTO> detalles;
}
