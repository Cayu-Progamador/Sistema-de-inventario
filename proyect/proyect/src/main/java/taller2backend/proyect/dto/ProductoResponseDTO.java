package taller2backend.proyect.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
    private Long idProducto;
    private String codigoProducto;
    private String nombreProducto;
    private String tipoProducto;
    private String unidadMedida;
    private Double precio;
    private String codigoBarra;
    private Date fechaVencimiento;

    // Inventario
    private Integer stockActual;
    private Integer stockMinimo;
    private Integer stockMaximo;
    private String ubicacion;
}
