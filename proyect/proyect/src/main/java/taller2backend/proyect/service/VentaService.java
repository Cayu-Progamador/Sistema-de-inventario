package taller2backend.proyect.service;

import java.util.List;

import taller2backend.proyect.dto.DetalleDto;
import taller2backend.proyect.dto.HistorialProductoDTO;
import taller2backend.proyect.dto.VentaDTO;
import taller2backend.proyect.dto.VentaListadoDto;
import taller2backend.proyect.dto.VentaRequestDto;
import taller2backend.proyect.entity.Venta;

public interface VentaService {
    // ver las ventas que se han realizado
    public List<VentaDTO> obtenerVentasConDetalle();

    public Venta registrarVenta(VentaRequestDto ventaRequest);

    public List<HistorialProductoDTO> historialCliente(Long idCliente);






    public List<VentaListadoDto> listarVentas() ;


    public DetalleDto obtenerDetalleVenta(Long id);
}
