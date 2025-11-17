package taller2backend.proyect.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import taller2backend.proyect.dto.ProductoDTO;
import taller2backend.proyect.dto.ProductoResponseDTO;
import taller2backend.proyect.entity.Inventario;
import taller2backend.proyect.entity.Producto;
import taller2backend.proyect.repository.InventarioRepository;
import taller2backend.proyect.repository.ProductoRepository;
import taller2backend.proyect.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final InventarioRepository inventarioRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, InventarioRepository inventarioRepository) {
        this.productoRepository = productoRepository;
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public Optional<Producto> buscarPorCodigoBarra(String codigoBarra) {
        return productoRepository.findByCodigoBarra(codigoBarra);
    }

    @Override
    public List<Producto> buscarProductosPorTermino(String termino) {
        return productoRepository.buscarPorTermino(termino);
    }

    @Override
    public List<String> listarTiposDeProducto() {
        return productoRepository.findDistinctTipoProducto();
    }

    @Override
    public List<Producto> listarProductosPorTipo(String tipo) {
        return productoRepository.findByTipoProducto(tipo);
    }

    // Crear producto con inventario
    @Override
    @Transactional
    public ProductoResponseDTO crearProducto(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setPrecio(dto.getPrecio());
        producto.setTipoProducto(dto.getTipoProducto());
        producto.setCodigoBarra(dto.getCodigoBarra());
        producto.setFechaVencimiento(dto.getFechaVencimiento());
        producto.setUnidadMedida(dto.getUnidadMedida());

        // Guardar producto para generar id
        Producto productoGuardado = productoRepository.save(producto);

        // Generar códigoProducto
        productoGuardado.setCodigoProducto("P-" + productoGuardado.getIdProducto());
        productoRepository.save(productoGuardado);

        // Crear inventario
        Inventario inventario = new Inventario();
        inventario.setProducto(productoGuardado);
        inventario.setStockActual(dto.getStockActual());
        inventario.setStockMinimo(dto.getStockMinimo());
        inventario.setStockMaximo(dto.getStockMaximo());
        inventario.setUbicacion(dto.getUbicacion());
        inventario.setFechaActualizacion(new Date());

        inventarioRepository.save(inventario);

        // Retornar DTO
        return new ProductoResponseDTO(
                productoGuardado.getIdProducto(),
                productoGuardado.getCodigoProducto(),
                productoGuardado.getNombreProducto(),
                productoGuardado.getTipoProducto(),
                productoGuardado.getUnidadMedida(),
                productoGuardado.getPrecio(),
                productoGuardado.getCodigoBarra(),
                productoGuardado.getFechaVencimiento(),
                inventario.getStockActual(),
                inventario.getStockMinimo(),
                inventario.getStockMaximo(),
                inventario.getUbicacion());
    }

    // listar todo los productos
    @Override
    public List<ProductoResponseDTO> listarTodosProductos() {
        List<Producto> productos = productoRepository.findAll();

        // Convertimos a ProductoResponseDTO incluyendo inventario
        return productos.stream().map(producto -> {
            // Buscamos el inventario relacionado
            Optional<Inventario> inventarioOpt = inventarioRepository.findByProducto(producto);
            Inventario inventario = inventarioOpt.orElse(new Inventario()); // Evitar null

            return new ProductoResponseDTO(
                    producto.getIdProducto(),
                    producto.getCodigoProducto(),
                    producto.getNombreProducto(),
                    producto.getTipoProducto(),
                    producto.getUnidadMedida(),
                    producto.getPrecio(),
                    producto.getCodigoBarra(),
                    producto.getFechaVencimiento(),
                    inventario.getStockActual(),
                    inventario.getStockMinimo(),
                    inventario.getStockMaximo(),
                    inventario.getUbicacion());
        }).toList();

    }

    // actualizar producto
    @Override
    @Transactional
    public ProductoResponseDTO actualizarProducto(Long idProducto, ProductoDTO dto) {
        // 1. Buscar el producto existente
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + idProducto));

        // 2. Actualizar campos del producto
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setPrecio(dto.getPrecio());
        producto.setTipoProducto(dto.getTipoProducto());
        producto.setCodigoBarra(dto.getCodigoBarra());
        producto.setFechaVencimiento(dto.getFechaVencimiento());
        producto.setUnidadMedida(dto.getUnidadMedida());

        productoRepository.save(producto);

        // 3. Buscar inventario existente
        Inventario inventario = inventarioRepository.findByProducto(producto)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado para el producto id: " + idProducto));

        // 4. Actualizar campos del inventario
        inventario.setStockActual(dto.getStockActual());
        inventario.setStockMinimo(dto.getStockMinimo());
        inventario.setStockMaximo(dto.getStockMaximo());
        inventario.setUbicacion(dto.getUbicacion());
        inventario.setFechaActualizacion(new Date());

        inventarioRepository.save(inventario);

        // 5. Retornar DTO actualizado
        return new ProductoResponseDTO(
                producto.getIdProducto(),
                producto.getCodigoProducto(),
                producto.getNombreProducto(),
                producto.getTipoProducto(),
                producto.getUnidadMedida(),
                producto.getPrecio(),
                producto.getCodigoBarra(),
                producto.getFechaVencimiento(),
                inventario.getStockActual(),
                inventario.getStockMinimo(),
                inventario.getStockMaximo(),
                inventario.getUbicacion());
    }

    // eliminar producto
    @Override
    @Transactional
    public void eliminarProducto(Long idProducto) {
        // 1. Buscar el producto
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + idProducto));

        // 2. Buscar inventario y eliminarlo primero
        inventarioRepository.findByProducto(producto)
                .ifPresent(inventarioRepository::delete);

        // 3. Eliminar el producto
        productoRepository.delete(producto);
    }

    // obtener los productos por id
    @Override
    public ProductoResponseDTO obtenerProductoPorId(Long idProducto) {
        // 1️⃣ Buscar el producto por su id
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + idProducto));

        // 2️⃣ Buscar el inventario asociado al producto
        Inventario inventario = inventarioRepository.findByProducto(producto)
                .orElse(new Inventario()); // Evita null si no tiene inventario

        // 3️⃣ Devolver el producto con los datos de inventario
        return new ProductoResponseDTO(
                producto.getIdProducto(),
                producto.getCodigoProducto(),
                producto.getNombreProducto(),
                producto.getTipoProducto(),
                producto.getUnidadMedida(),
                producto.getPrecio(),
                producto.getCodigoBarra(),
                producto.getFechaVencimiento(),
                inventario.getStockActual(),
                inventario.getStockMinimo(),
                inventario.getStockMaximo(),
                inventario.getUbicacion());
    }

    // buscar productos por nombre
    @Override
    public List<ProductoResponseDTO> buscarProductosPorNombre(String nombre) {
        List<Producto> productos = productoRepository.findByNombre(nombre);

        return productos.stream().map(producto -> {
            Inventario inventario = inventarioRepository.findByProducto(producto)
                    .orElse(new Inventario()); // Evitar null

            return new ProductoResponseDTO(
                    producto.getIdProducto(),
                    producto.getCodigoProducto(),
                    producto.getNombreProducto(),
                    producto.getTipoProducto(),
                    producto.getUnidadMedida(),
                    producto.getPrecio(),
                    producto.getCodigoBarra(),
                    producto.getFechaVencimiento(),
                    inventario.getStockActual(),
                    inventario.getStockMinimo(),
                    inventario.getStockMaximo(),
                    inventario.getUbicacion());
        }).toList();
    }

}
