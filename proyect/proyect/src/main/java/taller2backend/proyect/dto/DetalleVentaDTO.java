package taller2backend.proyect.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaDTO {
    private Long idProducto;
    private Integer cantidad;
    private Double precioUnitario;
}
