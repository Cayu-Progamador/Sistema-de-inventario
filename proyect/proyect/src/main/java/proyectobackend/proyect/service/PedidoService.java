package proyectobackend.proyect.service;

import java.util.List;

import proyectobackend.proyect.dto.PedidoDTO;

public interface PedidoService {
    //relizar pedido a proveedor 
    PedidoDTO realizarPedidoProveedor(PedidoDTO pedidoDTO);

    //lista de pedidos al proveedor 
    List<PedidoDTO> listaPedidoProveedor();

}
