package proyectobackend.proyect.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyectobackend.proyect.dto.DetallePedidoDTO;
import proyectobackend.proyect.dto.PedidoDTO;
import proyectobackend.proyect.entity.DetallePedido;
import proyectobackend.proyect.entity.Pedido;
import proyectobackend.proyect.entity.ProductoProveedor;
import proyectobackend.proyect.entity.Proveedor;
import proyectobackend.proyect.repository.PedidoRepository;
import proyectobackend.proyect.repository.ProductoProveedorRepository;
import proyectobackend.proyect.repository.ProveedorRepository;
import proyectobackend.proyect.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

    // inyector los repositorios
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProductoProveedorRepository productoProveedorRepository;

    // realizar pedido a proveedor
    
    // realizar pedido a proveedor
    @Override
public PedidoDTO realizarPedidoProveedor(PedidoDTO pedidoDTO) {
    // Validar que vengan detalles
    if (pedidoDTO.getDetalles() == null || pedidoDTO.getDetalles().isEmpty()) {
        throw new RuntimeException("No se enviaron detalles del pedido");
    }

    // Buscar proveedor
    Proveedor proveedor = proveedorRepository.findById(pedidoDTO.getIdProveedor())
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + pedidoDTO.getIdProveedor()));

    // Crear nuevo pedido
    Pedido pedido = new Pedido();
    pedido.setProveedor(proveedor);
    pedido.setFechaPedido(new Date());
    pedido.setFechaEntrega(pedidoDTO.getFechaEntrega());
    pedido.setEstado("Pendiente");

    // Inicializar lista de detalles en el pedido
    pedido.setDetallePedidos(new ArrayList<>()); // Inicializar lista de detalles

    double total = 0.0;

    // Recorrer los detalles del pedido
    for (DetallePedidoDTO detalleDTO : pedidoDTO.getDetalles()) {
        // Buscar el ProductoProveedor usando IDs
        ProductoProveedor pp = productoProveedorRepository
                .findByProducto_IdProductoAndProveedor_IdProveedor(detalleDTO.getIdProducto(), proveedor.getIdProveedor())
                .orElseThrow(() -> new RuntimeException(
                        "El producto con ID " + detalleDTO.getIdProducto() +
                        " no está asociado al proveedor con ID " + proveedor.getIdProveedor()));

        // Crear el detalle del pedido
        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setProducto(pp.getProducto());
        detalle.setCantidad(detalleDTO.getCantidad());
        detalle.setSubtotal(detalleDTO.getCantidad() * pp.getPrecioCompra());

        // Agregar detalle a la lista del pedido
        pedido.getDetallePedidos().add(detalle);

        // Sumar al total
        total += detalle.getSubtotal();
    }

    // Guardar pedido y detalles juntos gracias al Cascade
    pedido.setTotalPedido(total);
    pedido = pedidoRepository.save(pedido);

    // Preparar respuesta DTO
    PedidoDTO respuesta = new PedidoDTO();
    respuesta.setIdPedido(pedido.getIdPedido());
    respuesta.setIdProveedor(proveedor.getIdProveedor());
    respuesta.setFechaPedido(pedido.getFechaPedido());
    respuesta.setFechaEntrega(pedido.getFechaEntrega());
    respuesta.setTotalPedido(pedido.getTotalPedido());
    respuesta.setEstado(pedido.getEstado());

    // Mapear detalles
    List<DetallePedidoDTO> detallesDTO = pedido.getDetallePedidos().stream().map(d -> {
        DetallePedidoDTO ddto = new DetallePedidoDTO();
        ddto.setIdProducto(d.getProducto().getIdProducto());
        ddto.setCantidad(d.getCantidad());
        ddto.setSubtotal(d.getSubtotal());
        return ddto;
    }).collect(Collectors.toList());

    respuesta.setDetalles(detallesDTO);

    return respuesta;
}



    // lista de pedidos al proveedor
    @Override
    public List<PedidoDTO> listaPedidoProveedor() {
        return pedidoRepository.findAll().stream().map(p -> {
            PedidoDTO dto = new PedidoDTO();
            dto.setIdPedido(p.getIdPedido());
            dto.setIdProveedor(p.getProveedor().getIdProveedor());
            dto.setFechaPedido(p.getFechaPedido());
            dto.setFechaEntrega(p.getFechaEntrega());
            dto.setTotalPedido(p.getTotalPedido());
            dto.setEstado(p.getEstado());
            dto.setDetalles(p.getDetallePedidos().stream().map(d -> {
                DetallePedidoDTO ddto = new DetallePedidoDTO();
                ddto.setIdProducto(d.getProducto().getIdProducto());
                ddto.setCantidad(d.getCantidad());
                ddto.setSubtotal(d.getSubtotal());
                return ddto;
            }).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
    }

}
