package proyectobackend.proyect.service;

import java.util.List;

import proyectobackend.proyect.dto.DetalleDto;
import proyectobackend.proyect.dto.HistorialProductoDTO;
import proyectobackend.proyect.dto.VentaDTO;
import proyectobackend.proyect.dto.VentaListadoDto;
import proyectobackend.proyect.dto.VentaRequestDto;
import proyectobackend.proyect.entity.Venta;

public interface VentaService {
    // ver las ventas que se han realizado
    public List<VentaDTO> obtenerVentasConDetalle();

    public Venta registrarVenta(VentaRequestDto ventaRequest);

    public List<HistorialProductoDTO> historialCliente(Long idCliente);






    public List<VentaListadoDto> listarVentas() ;


    public DetalleDto obtenerDetalleVenta(Long id);
}
