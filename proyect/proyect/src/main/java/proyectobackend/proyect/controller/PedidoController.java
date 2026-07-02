package proyectobackend.proyect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyectobackend.proyect.dto.PedidoDTO;
import proyectobackend.proyect.service.PedidoService;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;

    //creaer pedido
    @PostMapping("/crear")
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO pedidoCreado = pedidoService.realizarPedidoProveedor(pedidoDTO);
        return ResponseEntity.ok(pedidoCreado);
    }
    //lista de pedidos 

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listaPedidoProveedor());
    }
}
