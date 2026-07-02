package proyectobackend.proyect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyectobackend.proyect.entity.Inventario;
import proyectobackend.proyect.entity.Producto;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario,Long> {
    // Este es el método mágico que usaste en el servicio.
    // Spring crea la consulta automáticamente basado en el nombre del método.
    Optional<Inventario> findByProducto(Producto producto);
    
    // Opcionalmente (si prefieres buscar por ID):
    Optional<Inventario> findByProducto_IdProducto(Long idProducto);
}
