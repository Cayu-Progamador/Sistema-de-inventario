package taller2backend.proyect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import taller2backend.proyect.dto.ClienteListDTO;
import taller2backend.proyect.entity.Cliente;

public interface ClienteService {
    @Autowired

    // buscar todos los datos
    public Cliente addClient(Cliente cliente);

    //eliminar cleente por id 
    public void deteleClient(Long idCliente);


    //actualizar datos del cliente
    public Cliente updateClient(Long idCliente, Cliente clienteNuevo);
    
//buscar cliente por nombre
    public List<ClienteListDTO> buscarClientPorName(String name);

    //Lista de todos los clientes
    public List<ClienteListDTO> listarClientes();
    
    //Obtener cliente por id
    public Cliente obtenerClientePorId(Long idCliente);
    // Lista de todos los clientes que tienen una ventas

}
