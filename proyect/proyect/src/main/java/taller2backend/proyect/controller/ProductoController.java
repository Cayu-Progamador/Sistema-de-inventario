package taller2backend.proyect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import taller2backend.proyect.dto.ProductoDTO;
import taller2backend.proyect.dto.ProductoProveedorDTO;
import taller2backend.proyect.dto.ProductoResponseDTO;
import taller2backend.proyect.entity.Producto;
import taller2backend.proyect.service.ProductoProveedorService;
import taller2backend.proyect.service.ProductoService;

import java.util.List;

@RestController

@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    private ProductoProveedorService productoProveedorService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // --- Endpoints para la Interfaz 2 (Código de Barras) ---

    @GetMapping("/barcode/{codigo}")
    public ResponseEntity<Producto> getProductoPorBarcode(@PathVariable String codigo) {
        return productoService.buscarPorCodigoBarra(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarProductosPorTermino(@RequestParam String termino) {
        return ResponseEntity.ok(productoService.buscarProductosPorTermino(termino));
    }

    // --- Endpoints para la Interfaz 1 (A Granel) ---

    @GetMapping("/tipos")
    public ResponseEntity<List<String>> getTiposDeProducto() {
        return ResponseEntity.ok(productoService.listarTiposDeProducto());
    }

    @GetMapping("/por-tipo/{tipo}")
    public ResponseEntity<List<Producto>> getProductosPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(productoService.listarProductosPorTipo(tipo));
    }

    // agregar producto

    @PostMapping("/agregar")
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
        try {
            ProductoResponseDTO productoGuardo = productoService.crearProducto(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // ver los productos en producto proveedor la relacion
    @GetMapping("/ver")
    public List<ProductoProveedorDTO> listarTodos() {
        return productoProveedorService.listarTodosDTO();
    }

    // ver los productos en producto proveedor la relacion
    @GetMapping("/todos")
    public List<ProductoResponseDTO> obtenerTodosProductos() {
        return productoService.listarTodosProductos();
    }

    // editar productos
    @PutMapping("/{id}")
    public ProductoResponseDTO editarProducto(
            @PathVariable Long id,
            @RequestBody ProductoDTO dto) {
        return productoService.actualizarProducto(id, dto);
    }

    // eliminar producto
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

    // obtener producto por id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Long id) {
        try {
            ProductoResponseDTO producto = productoService.obtenerProductoPorId(id);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto no encontrado con id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el producto: " + e.getMessage());
        }
    }

    @GetMapping("/nombre")
    public List<ProductoResponseDTO> buscarPorNombre(@RequestParam String nombre) {
        return productoService.buscarProductosPorNombre(nombre);
    }

}