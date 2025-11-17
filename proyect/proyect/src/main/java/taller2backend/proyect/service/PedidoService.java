package taller2backend.proyect.service;

import java.util.List;

import taller2backend.proyect.dto.PedidoDTO;

public interface PedidoService {
    //relizar pedido a proveedor 
    PedidoDTO realizarPedidoProveedor(PedidoDTO pedidoDTO);

    //lista de pedidos al proveedor 
    List<PedidoDTO> listaPedidoProveedor();

}
