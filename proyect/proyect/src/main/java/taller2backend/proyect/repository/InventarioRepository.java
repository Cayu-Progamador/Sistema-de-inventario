package taller2backend.proyect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import taller2backend.proyect.entity.Inventario;
import taller2backend.proyect.entity.Producto;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario,Long> {
    // Este es el método mágico que usaste en el servicio.
    // Spring crea la consulta automáticamente basado en el nombre del método.
    Optional<Inventario> findByProducto(Producto producto);
    
    // Opcionalmente (si prefieres buscar por ID):
    Optional<Inventario> findByProducto_IdProducto(Long idProducto);
}
