package proyectobackend.proyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyectobackend.proyect.entity.DocumentoVenta;

@Repository
public interface DocumentoVentaRepository  extends JpaRepository<DocumentoVenta,Long>{

}
