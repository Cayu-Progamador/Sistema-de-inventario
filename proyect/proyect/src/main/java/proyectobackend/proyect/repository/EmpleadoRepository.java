package proyectobackend.proyect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proyectobackend.proyect.entity.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{
    //buscar empleado por nombre 
    @Query("SELECT e FROM Empleado e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :terminoBusqueda, '%'))")
    List<Empleado> bucarEmpleadoPorNombre(@Param("terminoBusqueda") String nombre);
}
