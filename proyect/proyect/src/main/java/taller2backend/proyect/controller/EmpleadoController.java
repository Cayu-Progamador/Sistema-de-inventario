package taller2backend.proyect.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import taller2backend.proyect.entity.Empleado;
import taller2backend.proyect.service.EmpleadoService;

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService){
        this.empleadoService = empleadoService;
    }

    //ver todos los empleados en forma de lista 
    @GetMapping("/ver-empleados")
    public List<Empleado> getEmpleados(){
        return empleadoService.obtenerDatos();
    }

    //añdir datos del empleado
    @PostMapping("/agregar")
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleado){
        Empleado nuevoEmpleado = empleadoService.agregarEmpleado(empleado);
        return new ResponseEntity<>(nuevoEmpleado, HttpStatus.CREATED);
    }

    //actualizar empleado
    @PutMapping("/update/{idEmpleado}")
    public Empleado actualizarEmpleado(@PathVariable("idEmpleado") long  idEmpleado, @RequestBody Empleado empleadoDetalles){
        return empleadoService.actualizarEmpleado(idEmpleado, empleadoDetalles);
    }

    //eliminar empleado
    @DeleteMapping("/eliminar/{idEmpleado}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable("idEmpleado") long idEmpleado){
        empleadoService.eliminarEmpleado(idEmpleado);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //buscar empleado por nombre
    @GetMapping("/buscar")
    public List<Empleado> bucarEmpleadoPorNombre(@RequestParam("nombre") String nombre){
        return empleadoService.buscarEmpleadoPorNombre(nombre);
    }

    //obtener empleador por id 
    @GetMapping("/obtener/{idEmpleado}")
    public Empleado obtenerEmpleadoPorId(@PathVariable("idEmpleado") long idEmpleado){
        return empleadoService.obtenerEmpeadoPorId(idEmpleado);
    }
}
