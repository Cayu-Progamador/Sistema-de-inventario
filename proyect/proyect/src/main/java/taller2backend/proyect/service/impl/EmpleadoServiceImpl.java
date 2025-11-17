package taller2backend.proyect.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import taller2backend.proyect.entity.Empleado;
import taller2backend.proyect.repository.EmpleadoRepository;
import taller2backend.proyect.service.EmpleadoService;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    // listar todos los empleados
    @Override
    public List<Empleado> obtenerDatos() {
        return empleadoRepository.findAll();
    }

    // obtener empleado por id
    @Override
    public Empleado obtenerEmpeadoPorId(Long idEmnpleado) {
        return empleadoRepository.findById(idEmnpleado)
                .orElseThrow(()-> new RuntimeException("Empleado no encontrado con Id: "+idEmnpleado));
    }


    // agregar empleado
    @Override
    public Empleado agregarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    // actualizar empleado
    @Override
    public Empleado actualizarEmpleado(Long idEmpleado, Empleado empleado) {
        Empleado empleadoNuevo = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Empleado no encontrado con ID: " + idEmpleado));
        empleadoNuevo.setNombre(empleado.getNombre());
        empleadoNuevo.setCi(empleado.getCi());
        empleadoNuevo.setApellido(empleado.getApellido());
        empleadoNuevo.setDireccion(empleado.getDireccion());
        empleadoNuevo.setTelefono(empleado.getTelefono());
        empleadoNuevo.setCorreo(empleado.getCorreo());
        empleadoNuevo.setEstado(empleado.getEstado());
        return empleadoRepository.save(empleadoNuevo);
    }

    // eliminar empleado
    @Override
    public void eliminarEmpleado(Long idEmpleado) {
        if (!empleadoRepository.existsById(idEmpleado)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado con ID: " + idEmpleado);
        }
        empleadoRepository.deleteById(idEmpleado);
    }

    // buscar empleado por nombre
    @Override
    public List<Empleado> buscarEmpleadoPorNombre(String nombre) {
        return empleadoRepository.bucarEmpleadoPorNombre(nombre);
    }
}
