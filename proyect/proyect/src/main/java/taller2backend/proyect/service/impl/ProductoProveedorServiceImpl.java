package taller2backend.proyect.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import taller2backend.proyect.dto.ProductoProveedorDTO;
import taller2backend.proyect.repository.ProductoProveedorRepository;
import taller2backend.proyect.service.ProductoProveedorService;

@Service
public class ProductoProveedorServiceImpl implements ProductoProveedorService{

    @Autowired
    private ProductoProveedorRepository productoProveedorRepository;
  
    
     @Override
    public List<ProductoProveedorDTO> listarTodosDTO() {
        return productoProveedorRepository.findAll().stream().map(pp -> {
            ProductoProveedorDTO dto = new ProductoProveedorDTO();
            dto.setIdProductoProveedor(pp.getIdProductoProveedor());
            dto.setNombreProducto(pp.getProducto().getNombreProducto());
            dto.setNombreProveedor(pp.getProveedor().getNombre());
            dto.setPrecioCompra(pp.getPrecioCompra());
            return dto;
        }).collect(Collectors.toList());
    }

}
