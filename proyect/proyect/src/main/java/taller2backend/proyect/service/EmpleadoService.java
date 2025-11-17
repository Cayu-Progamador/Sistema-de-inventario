package taller2backend.proyect.service;

import java.util.List;
import taller2backend.proyect.entity.Empleado;

public interface EmpleadoService {

    //lisatr todos los empleados
   
    public List<Empleado> obtenerDatos();

    //obtener empleado por id
    public Empleado obtenerEmpeadoPorId(Long idEmnpleado);

    //agregar empleado
    public Empleado agregarEmpleado(Empleado empleado);

    //actualizar empleado
    public Empleado actualizarEmpleado(Long idEmpleado,Empleado empleado);

    //eliminar empleado
    public void eliminarEmpleado(Long idEmpleado);

    //buscar empleado por nombre
    public List<Empleado> buscarEmpleadoPorNombre(String nombre);



}
