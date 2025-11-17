package taller2backend.proyect.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import taller2backend.proyect.dto.ClienteListDTO;
import taller2backend.proyect.entity.Cliente;
import taller2backend.proyect.service.ClienteService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	private final ClienteService clienteService;

	
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	//añadir un nuevo cliente
	@PostMapping("/anadir")
	public ResponseEntity<Cliente> addClientDat(@RequestBody Cliente cliente){
		try {
			Cliente añadirNuevoCliente = clienteService.addClient(cliente);
			return ResponseEntity.status(HttpStatus.CREATED).body(añadirNuevoCliente);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}


	// actualizar cliente
	@PutMapping("update/{idCliente}")
	public ResponseEntity<?> updateClient(@PathVariable Long idCliente, @RequestBody Cliente clienteListDTO) {
		try {

			Cliente actualizarCliente = clienteService.updateClient(idCliente, clienteListDTO);
			return ResponseEntity.status(HttpStatus.OK).body(actualizarCliente);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	//Elimnar cliente
	@DeleteMapping("eliminar-client/{idCliente}")
	public ResponseEntity<?> deleteClientData(@PathVariable Long idCliente ) {
		clienteService.deteleClient(idCliente);
		return ResponseEntity.status(HttpStatus.OK).body("Cliente eliminado");
	}


	//bucar por nombre del cliente
	@GetMapping("/buscar-cliente")
	public ResponseEntity<List<ClienteListDTO>> buscarCliente(@RequestParam String nombre) {
		List<ClienteListDTO> clienteBuscado = clienteService.buscarClientPorName(nombre);
		return ResponseEntity.ok(clienteBuscado);
	}

	//Lista de todos los clientes
	@GetMapping("/listar-cliente")
	public ResponseEntity<List<ClienteListDTO>> listarCliente() {
		List<ClienteListDTO> clienteObtenido = clienteService.listarClientes();
		return ResponseEntity.ok(clienteObtenido);

	}

	//Obtener cliente por id
	@GetMapping("/obtener-cliente/{idCliente}")
	public ResponseEntity<Cliente> obtenerClientPorId(@PathVariable Long idCliente) {
		return ResponseEntity.ok(clienteService.obtenerClientePorId(idCliente));
	}

	//Lista de todos los clientes que tienen una ventas
	


	
}
