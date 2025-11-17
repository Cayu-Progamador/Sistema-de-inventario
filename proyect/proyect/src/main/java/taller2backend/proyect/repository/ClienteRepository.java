package taller2backend.proyect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import taller2backend.proyect.dto.ClienteListDTO;
import taller2backend.proyect.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // buscar todos los datos solo por nomb
    @Query("SELECT new taller2backend.proyect.dto.ClienteListDTO(" +
            "    c.idCliente, c.nombre, c.ci, c.correoElectronico, c.direccion, c.telefono" +
            ") " +
            "FROM Cliente c " +
            "WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    public List<ClienteListDTO> findByNameClient(@Param("nombre") String nombre);
//todos los datos de los clientes
    
    @Query("SELECT new taller2backend.proyect.dto.ClienteListDTO(" +
            "    c.idCliente, " +
            "    c.nombre, " +
            "    c.ci, " +
            "    c.correoElectronico, " +
            "    c.direccion, " +
            "    c.telefono" +
            ") " +
            "FROM Cliente c")
    List<ClienteListDTO> findAllClientesDTO();
}
