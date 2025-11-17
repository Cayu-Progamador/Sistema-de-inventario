package taller2backend.proyect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoProveedorDTO {
    private Long idProductoProveedor;
    private String nombreProducto;
    private String nombreProveedor;
    private Double precioCompra;
}
