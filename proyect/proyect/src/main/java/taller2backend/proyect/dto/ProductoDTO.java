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
public class ProductoDTO {
    private String tipoProducto;
    private String codigoBarra;
    private String codigoProducto;
    private Date fechaVencimiento;
    private String unidadMedida;
    private String nombreProducto;
    private Double precio;

    // Inventario
    private Integer stockActual;
    private Integer stockMinimo;
    private Integer stockMaximo;
    private String ubicacion;
}
