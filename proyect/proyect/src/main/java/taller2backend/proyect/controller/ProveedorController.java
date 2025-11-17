package taller2backend.proyect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import taller2backend.proyect.dto.ProveedorDTO;
import taller2backend.proyect.service.ProveedorService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/proveedor")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    //LISTAR PROVEEDORES
    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> listarProveedores(){
        return ResponseEntity.ok(proveedorService.listarProveedores());
    }

    //crear proveedor
    @PostMapping("/crear")
    public ResponseEntity<ProveedorDTO> crearProveedor(@RequestBody ProveedorDTO proveedorDTO) {
          ProveedorDTO nuevoProveedor = proveedorService.crearProveedor(proveedorDTO);
        return new ResponseEntity<>(nuevoProveedor, HttpStatus.CREATED);
    }

    //ELIMINAR PROVEEDOR 

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }

    //actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> updateProveedor(@PathVariable Long id, @RequestBody ProveedorDTO proveedorDTO) {
        ProveedorDTO datosNuevos = proveedorService.actualizarProveedor(id, proveedorDTO);
        return ResponseEntity.ok(datosNuevos);
    }

    //  buscar por nombre
     @GetMapping("/buscar")
    public ResponseEntity<List<ProveedorDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(proveedorService.listarProveedoresPorNombre(nombre));
    }
    
    //obtener proveedor por id
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> obtenerPorId(@PathVariable Long id) {
        return proveedorService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
