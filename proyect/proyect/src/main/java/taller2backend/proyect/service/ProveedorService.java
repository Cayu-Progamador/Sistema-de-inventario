package taller2backend.proyect.service;
import java.util.List;
import java.util.Optional;

import taller2backend.proyect.dto.*;

public interface ProveedorService {


    //listar proveedores
    List<ProveedorDTO> listarProveedores();

    //crear proveedor
    public ProveedorDTO crearProveedor(ProveedorDTO proveedorDto);
    
    //actualizar proveedor
    public ProveedorDTO actualizarProveedor(Long  id, ProveedorDTO proveedorDto);
    //eliminar proveedor
    public void eliminarProveedor(Long id);

    //listar proveedores por nombre
    public List<ProveedorDTO> listarProveedoresPorNombre(String nombre);

    //obtener proveedor por id
    Optional<ProveedorDTO> obtenerPorId(Long id);
}
