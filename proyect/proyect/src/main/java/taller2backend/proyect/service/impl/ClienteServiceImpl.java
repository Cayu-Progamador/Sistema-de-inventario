package taller2backend.proyect.service.impl;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import taller2backend.proyect.dto.ClienteListDTO;
import taller2backend.proyect.entity.Cliente;
import taller2backend.proyect.repository.ClienteRepository;
import taller2backend.proyect.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // añadir los datos del cliente
    @Override
    public Cliente addClient(Cliente cliente) {
        try {

            if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
                throw new IllegalArgumentException("No puede quedar vacio el nombre del cliente");
            }
            return clienteRepository.save(cliente);

        } catch (IllegalArgumentException e) {
            throw new RuntimeErrorException(null, e.getMessage());
        }
    }

    // eliminar cliente por id
    @Override
    public void deteleClient(Long idCliente) {
        try {
            if (!clienteRepository.existsById(idCliente)) {
                throw new IllegalArgumentException("El cliente no existe busca de nuevo con el id correcto");
            }
            clienteRepository.deleteById(idCliente);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // actualizar los datos del cliente
    @Override
    public Cliente updateClient(Long idCliente, Cliente clienteNuevo) {
        Cliente clienteEnDB = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("El cliente no existe"));
        //actualizar los datos del cliente
        clienteEnDB.setNombre(clienteNuevo.getNombre());
        clienteEnDB.setCi(clienteNuevo.getCi());
        clienteEnDB.setCorreoElectronico(clienteNuevo.getCorreoElectronico());
        clienteEnDB.setDireccion(clienteNuevo.getDireccion());
        clienteEnDB.setTelefono(clienteNuevo.getTelefono());
        

        return clienteRepository.save(clienteEnDB);
    }

    // buscar nombre del cliente
    @Override
    public List<ClienteListDTO> buscarClientPorName(String name) {
        return clienteRepository.findByNameClient(name);
    }

    // ;ista de clientes todods
    @Override
    public List<ClienteListDTO> listarClientes() {
        return clienteRepository.findAllClientesDTO();
    }

    @Override
    public Cliente obtenerClientePorId(Long idCliente) {
        return clienteRepository.findById(idCliente).orElse(null);
    }

}
