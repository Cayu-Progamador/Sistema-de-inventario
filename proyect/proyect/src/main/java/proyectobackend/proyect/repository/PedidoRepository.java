package proyectobackend.proyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyectobackend.proyect.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
