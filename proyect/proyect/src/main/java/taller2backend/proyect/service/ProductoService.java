package taller2backend.proyect.service;

import java.util.List;
import java.util.Optional;

import taller2backend.proyect.dto.ProductoDTO;
import taller2backend.proyect.dto.ProductoResponseDTO;
import taller2backend.proyect.entity.Producto;

public interface ProductoService {

    //--Metodo para la interfaz 2
    public Optional<Producto> buscarPorCodigoBarra(String codigoBarra);


    public List<Producto> buscarProductosPorTermino(String termino);

    //metodo para la interfaz 1
    public List<String>listarTiposDeProducto();

    public List<Producto> listarProductosPorTipo(String tipo);

    public ProductoResponseDTO crearProducto(ProductoDTO productoDTO);

    //listar los productos
    List<ProductoResponseDTO> listarTodosProductos();
    //actualizar producto
    ProductoResponseDTO actualizarProducto(Long idProducto, ProductoDTO dto);

    //eliminar producto
    void eliminarProducto(Long idProducto);

    //obtener producto por id
    ProductoResponseDTO obtenerProductoPorId(Long idProducto);

    //buscar productos por nombre
    public List<ProductoResponseDTO> buscarProductosPorNombre(String nombre);

}
