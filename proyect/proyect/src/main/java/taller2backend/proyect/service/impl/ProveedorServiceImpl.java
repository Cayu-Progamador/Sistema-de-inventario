package taller2backend.proyect.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taller2backend.proyect.dto.ProveedorDTO;
import taller2backend.proyect.entity.Proveedor;
import taller2backend.proyect.repository.ProveedorRepository;
import taller2backend.proyect.service.ProveedorService;

@Service
public class ProveedorServiceImpl implements ProveedorService{

    @Autowired
    private ProveedorRepository proveedorRepository;


    //anadir un nuevo proveedor
    @Override
    @Transactional
    public ProveedorDTO crearProveedor(ProveedorDTO proveedorDto) {
        Proveedor proveedor = convertir_Entidad(proveedorDto);

        Proveedor proveedorGuardado = proveedorRepository.save(proveedor);
        return convetir_DTO(proveedorGuardado);
    }


    private ProveedorDTO convetir_DTO(Proveedor proveedor) {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setIdProveedor(proveedor.getIdProveedor());
        dto.setNit(proveedor.getNit());
        dto.setNombre(proveedor.getNombre());
        dto.setApellido(proveedor.getApellido());
        dto.setDireccion(proveedor.getDireccion());
        dto.setTelefono(proveedor.getTelefono());
        dto.setCorreoElectronico(proveedor.getCorreoElectronico());
        dto.setEstado(proveedor.getEstado());
        return dto;
    }


    private Proveedor convertir_Entidad(ProveedorDTO dto) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNit(dto.getNit());
        proveedor.setNombre(dto.getNombre());
        proveedor.setApellido(dto.getApellido());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setCorreoElectronico(dto.getCorreoElectronico());
        proveedor.setEstado(dto.getEstado());
        return proveedor;
    }


    //listar proveedores
    @Override
    @Transactional(readOnly = true)
    public List<ProveedorDTO> listarProveedores() {
        return proveedorRepository.findAll().stream().map(this::convetir_DTO).collect(Collectors.toList());        
    }


    //actualizar proveedor
    @Override
    @Transactional
    public ProveedorDTO actualizarProveedor(Long id, ProveedorDTO proveedorDto) {

        Proveedor existente = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el proveedor con id " + id));

        existente.setNit(proveedorDto.getNit());
        existente.setNombre(proveedorDto.getNombre());
        existente.setApellido(proveedorDto.getApellido());
        existente.setDireccion(proveedorDto.getDireccion());
        existente.setTelefono(proveedorDto.getTelefono());
        existente.setCorreoElectronico(proveedorDto.getCorreoElectronico());
        existente.setEstado(proveedorDto.getEstado());
        
        Proveedor actualizado = proveedorRepository.save(existente);

        return convetir_DTO(actualizado);
    }

 //eliminar proveedor
    @Override
    @Transactional
    public void eliminarProveedor(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe el proveedor con id " + id));
            //si tiene relaciones , para provenir eliminacion
        if ((proveedor.getPedidos() != null && !proveedor.getPedidos().isEmpty()) ||
            (proveedor.getEntregas() != null && !proveedor.getEntregas().isEmpty()) ||
            (proveedor.getProductoProveedor() != null && !proveedor.getProductoProveedor().isEmpty())) {
            throw new RuntimeException("No se puede eliminar el proveedor porque tiene registros relacionados.");
        }
        proveedorRepository.delete(proveedor);
    }


    //listar proveedores por nombre
    @Override
    @Transactional(readOnly = true)
    public List<ProveedorDTO> listarProveedoresPorNombre(String nombre) {
        return proveedorRepository.findByNombreContainingIgnoreCase(nombre)
                .stream().map(this::convetir_DTO).collect(Collectors.toList());
    }


    //obtener proveedor por id
    @Override
    @Transactional(readOnly = true)
    public Optional<ProveedorDTO> obtenerPorId(Long id) {
        return proveedorRepository.findById(id)
                .map(this::convetir_DTO);
    }
    
    


}
