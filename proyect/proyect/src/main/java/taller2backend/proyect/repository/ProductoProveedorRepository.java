package taller2backend.proyect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import taller2backend.proyect.entity.ProductoProveedor;

@Repository
public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedor,Long>{

Optional<ProductoProveedor> findByProducto_IdProductoAndProveedor_IdProveedor(Long idProducto, Long idProveedor);

}
