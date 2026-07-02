package proyectobackend.proyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyectobackend.proyect.entity.MetodoPago;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago,Long> {

}
