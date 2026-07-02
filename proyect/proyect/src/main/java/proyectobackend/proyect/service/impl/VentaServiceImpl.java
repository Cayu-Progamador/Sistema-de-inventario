package proyectobackend.proyect.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import proyectobackend.proyect.dto.DetalleDto;
// --- Importa tus DTOs ---
import proyectobackend.proyect.dto.DetalleVentaDTO;
import proyectobackend.proyect.dto.DetallesVentasDto;
import proyectobackend.proyect.dto.HistorialProductoDTO;
import proyectobackend.proyect.dto.VentaDTO;
import proyectobackend.proyect.dto.VentaListadoDto;
import proyectobackend.proyect.dto.VentaRequestDto;
import proyectobackend.proyect.entity.Cliente;
// --- Importa tus Entidades ---
import proyectobackend.proyect.entity.DetalleVenta;
import proyectobackend.proyect.entity.DocumentoVenta; // <-- ¡ESTE IMPORT FALTABA!
import proyectobackend.proyect.entity.Inventario;
import proyectobackend.proyect.entity.Producto;
import proyectobackend.proyect.entity.Venta;
import proyectobackend.proyect.entity.Empleado;
import proyectobackend.proyect.entity.MetodoPago;
import proyectobackend.proyect.repository.ClienteRepository;
// --- Importa tus Repositorios ---
import proyectobackend.proyect.repository.DetalleVentaRepository;
import proyectobackend.proyect.repository.DocumentoVentaRepository;
import proyectobackend.proyect.repository.InventarioRepository;
import proyectobackend.proyect.repository.ProductoRepository;
import proyectobackend.proyect.repository.VentaRepository;
import proyectobackend.proyect.repository.EmpleadoRepository;
import proyectobackend.proyect.repository.MetodoPagoRepository;

// --- Importa el Servicio y la Excepción ---
import proyectobackend.proyect.service.VentaService;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VentaServiceImpl implements VentaService {

    // --- Repositorios Inyectados ---
    private final ProductoRepository productoRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final VentaRepository ventaRepository;
    private final InventarioRepository inventarioRepository;
    private final EmpleadoRepository empleadoRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final ClienteRepository clienteRepository;
    private final DocumentoVentaRepository documentoVentaRepository;

    // --- Constructor Completo (Tu código está perfecto aquí) ---
    public VentaServiceImpl(ProductoRepository productoRepository,
            DetalleVentaRepository detalleVentaRepository,
            VentaRepository ventaRepository,
            InventarioRepository inventarioRepository,
            EmpleadoRepository empleadoRepository,
            MetodoPagoRepository metodoPagoRepository,
            ClienteRepository clienteRepository,
            DocumentoVentaRepository documentoVentaRepository) {
        this.productoRepository = productoRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.ventaRepository = ventaRepository;
        this.inventarioRepository = inventarioRepository;
        this.empleadoRepository = empleadoRepository;
        this.metodoPagoRepository = metodoPagoRepository;
        this.clienteRepository = clienteRepository;
        this.documentoVentaRepository = documentoVentaRepository;
    }

    @Override
    @Transactional
    public Venta registrarVenta(VentaRequestDto ventaRequest) {

        // --- 1. BUSCAR LAS ENTIDADES RELACIONADAS (Tu código está perfecto) ---
        Empleado empleado = empleadoRepository.findById(ventaRequest.getIdEmpleado())
                .orElseThrow(() -> new NoSuchElementException(
                        "No se encontró el empleado con ID: " + ventaRequest.getIdEmpleado()));

        MetodoPago metodoPago = metodoPagoRepository.findById(ventaRequest.getIdMetodoPago())
                .orElseThrow(() -> new NoSuchElementException(
                        "No se encontró el método de pago con ID: " + ventaRequest.getIdMetodoPago()));

        // buscar el cliente (Tu código está perfecto)
        Cliente cliente = null;
        if (ventaRequest.getIdCliente() != null) {
            cliente = clienteRepository.findById(ventaRequest.getIdCliente())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Cliente no encontrado con ID: " + ventaRequest.getIdCliente()));
        }
        // --- 2. CREAR Y GUARDAR LA VENTA (Tu código está perfecto) ---
        Venta nuevaVenta = new Venta();
        nuevaVenta.setEmpleado(empleado);
        nuevaVenta.setMetodoPago(metodoPago);
        nuevaVenta.setFechaVenta(new Date());
        nuevaVenta.setNumeroVenta("V-" + System.currentTimeMillis());
        nuevaVenta.setEstado("Completada");
        nuevaVenta.setTotalDescuento(ventaRequest.getTotalDescuento());
        Venta ventaGuardar = ventaRepository.save(nuevaVenta);

        // --- 3. INICIALIZAR ACUMULADOR (Tu código está perfecto) ---
        double subtotalCalculado = 0.0;

        // --- 4. a 9. BUCLE DE PRODUCTOS (Tu código está perfecto) ---
        for (DetalleVentaDTO detalleDTO : ventaRequest.getDetalles()) {
            // ... (Validar stock, restar stock, calcular subtotal, guardar detalle) ...
            // (Todo tu código existente aquí está bien)
            Producto producto = productoRepository.findById(detalleDTO.getIdProducto()).orElseThrow();
            Inventario inventario = inventarioRepository.findByProducto(producto).orElseThrow();
            if (inventario.getStockActual() < detalleDTO.getCantidad()) {
                throw new RuntimeException("No hay stock suficiente para: " + producto.getNombreProducto());
            }
            double precioReal = producto.getPrecio();
            int cantidad = detalleDTO.getCantidad();
            subtotalCalculado += (precioReal * cantidad);
            inventario.setStockActual(inventario.getStockActual() - cantidad);
            inventarioRepository.save(inventario);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(ventaGuardar);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precioReal);
            detalleVentaRepository.save(detalle);
        }

        // --- 10. ACTUALIZAR VENTA CON TOTALES (Tu código está perfecto) ---
        double totalCalculado = subtotalCalculado - ventaGuardar.getTotalDescuento();
        ventaGuardar.setSubtotal(subtotalCalculado);
        ventaGuardar.setTotalventa(totalCalculado);
        Venta ventaFinal = ventaRepository.save(ventaGuardar);

        // --- 11. ¡AQUÍ ESTÁ LA LÓGICA QUE FALTABA! ---
        // (Basado en tu entidad DocumentoVenta)

        DocumentoVenta doc = new DocumentoVenta();

        // Asigna las relaciones
        doc.setVenta(ventaFinal); // Enlaza con la Venta
        if (cliente != null) {
            doc.setCliente(cliente); // Enlaza con el Cliente
        }

        // Asigna los datos del DTO (asumiendo que actualizaste tu DTO)
        doc.setTipoDocumento(ventaRequest.getTipoDocumento());

        // Asigna los datos calculados por el backend
        doc.setFechaEmision(new Date());
        doc.setTotal(totalCalculado);

        // Calcula el impuesto (ej. 13% IVA, ajusta si es diferente)
        double impuestoCalculado = totalCalculado * 0.13;
        doc.setImpuesto(impuestoCalculado);

        // Genera un número de documento
        String numeroDoc = (ventaRequest.getTipoDocumento().equals("FACTURA") ? "F-" : "C-") + ventaFinal.getIdVenta();
        doc.setNumeroDocumento(numeroDoc);

        // Guarda el documento
        documentoVentaRepository.save(doc);

        return ventaFinal;
    }

    @Override
    public List<HistorialProductoDTO> historialCliente(Long idCliente) {
        return ventaRepository.findHistorialProductosByClienteId(idCliente);
    }

    // En tu VentaService.java
    public List<VentaDTO> obtenerVentasConDetalle() {
        return ventaRepository.listarVentasConDetalle();
    }

    // Obtener detalle de una venta por ID
    public DetalleDto obtenerDetalleVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id " + id));
        return convertirAVentaDetalleDto(venta);
    }

    private DetalleDto convertirAVentaDetalleDto(Venta venta) {
        List<DetallesVentasDto> detalles = venta.getDetalleVenta() != null ? venta.getDetalleVenta().stream()
                .map(det -> new DetallesVentasDto(
                        det.getProducto().getNombreProducto(),
                        det.getPrecioUnitario(),
                        det.getCantidad(),
                        det.getPrecioUnitario() * det.getCantidad()))
                .toList()
                : Collections.emptyList();

        return new DetalleDto(detalles);
    }

    /// lista todas las ventas realizadas
    @Transactional
    @Override
    public List<VentaListadoDto> listarVentas() {
        List<Venta> ventas = ventaRepository.findAll(); // traes todas las ventas
        return ventas.stream()
                .map(this::convertirAVentaListadoDto)
                .toList();
    }

    private VentaListadoDto convertirAVentaListadoDto(Venta venta) {
        return new VentaListadoDto(
                venta.getIdVenta(),
                venta.getFechaVenta(),
                venta.getNumeroVenta(),
                venta.getTotalventa(),
                venta.getSubtotal(),
                venta.getTotalDescuento(),
                venta.getEstado(),
                venta.getEmpleado().getNombre()
        );
    }

}