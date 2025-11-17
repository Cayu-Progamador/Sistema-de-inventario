package taller2backend.proyect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import taller2backend.proyect.entity.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor,Long>{

    List<Proveedor> findByNombreContainingIgnoreCase(String nombre);

}
