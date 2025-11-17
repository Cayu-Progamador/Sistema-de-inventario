package taller2backend.proyect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import taller2backend.proyect.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long>{
   
    //1. para el boton Escanear
    Optional<Producto> findByCodigoBarra(String codigoBarra);


    // 2. Para el campo "Buscar por código o nombre"
    @Query("SELECT p FROM Producto p WHERE " +
           "p.nombreProducto LIKE %:termino% OR " +
           "p.codigoProducto LIKE %:termino% OR " +
           "p.codigoBarra LIKE %:termino%")
    List<Producto> buscarPorTermino(@Param("termino") String termino);


    // 3. Para el primer desplegable ("A granel / Fresco")
    @Query("SELECT DISTINCT p.tipoProducto FROM Producto p")
    List<String> findDistinctTipoProducto();

    // 4. Para el segundo desplegable ("Pollo")
    List<Producto> findByTipoProducto(String tipoProducto);
    
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Producto> findByNombre(@Param("nombre") String nombre);
    
}
