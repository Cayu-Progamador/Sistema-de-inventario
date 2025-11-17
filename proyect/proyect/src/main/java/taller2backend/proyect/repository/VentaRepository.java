package taller2backend.proyect.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taller2backend.proyect.dto.HistorialProductoDTO;
import taller2backend.proyect.dto.VentaDTO;
import taller2backend.proyect.entity.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    // --- ASEGÚRATE DE QUE EL ORDEN SEA ESTE ---
    @Query("SELECT new taller2backend.proyect.dto.HistorialProductoDTO( " +
            "    v.idVenta, " + // 1. Long
            "    v.fechaVenta, " + // 2. Date
            "    v.numeroVenta, " + // 3. String
            "    mp.nombrePago, " + // 4. String (Metodo Pago)
            "    p.nombreProducto, " + // 5. String (Producto)
            "    dv.cantidad, " + // 6. Integer
            "    dv.precioUnitario, " + // 7. Double
            "    (dv.cantidad * dv.precioUnitario) " + // 8. Double
            ") " +
            "FROM Venta v " +
            "JOIN v.detalleVenta dv " +
            "JOIN dv.producto p " +
            "JOIN v.metodoPago mp " +
            "WHERE v.cliente.idCliente = :idCliente " +
            "ORDER BY v.fechaVenta DESC, p.nombreProducto ASC")
    List<HistorialProductoDTO> findHistorialProductosByClienteId(Long idCliente);

    
    @Query("SELECT new taller2backend.proyect.dto.VentaDTO(" +
            "v.idVenta, v.fechaVenta, p.nombreProducto, dv.cantidad, dv.precioUnitario, (dv.cantidad * dv.precioUnitario), e.nombre) "
            +
            "FROM Venta v " +
            "JOIN v.detalleVenta dv " +
            "JOIN dv.producto p " +
            "JOIN v.empleado e " +
            "ORDER BY v.fechaVenta DESC")
    List<VentaDTO> listarVentasConDetalle();

}