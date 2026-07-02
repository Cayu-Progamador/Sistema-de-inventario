package proyectobackend.proyect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import proyectobackend.proyect.entity.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta,Long>{
    @Query("SELECT d FROM DetalleVenta d WHERE d.venta.idVenta = :idVenta")
    List<DetalleVenta> findByVentaId(Long idVenta);
}
