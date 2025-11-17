package taller2backend.proyect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import taller2backend.proyect.dto.HistorialProductoDTO;
import taller2backend.proyect.dto.VentaDTO;
import taller2backend.proyect.dto.VentaRequestDto;
import taller2backend.proyect.entity.Venta;
import taller2backend.proyect.service.VentaService;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping("/registrar")
    public ResponseEntity<Venta> crearVenta(@RequestBody VentaRequestDto ventaResquest) {
        Venta ventaCreada = ventaService.registrarVenta(ventaResquest);
        return ResponseEntity.ok(ventaCreada);
    }

    //historial de compras del cliente
    @GetMapping("/historial/{idCliente}")
    public ResponseEntity<List<HistorialProductoDTO>> getHistorialCompras(@PathVariable Long idCliente) {
        List<HistorialProductoDTO> historial = ventaService.historialCliente(idCliente);
        if(historial.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historial);

    }

    //Listar la ventas
    @GetMapping("/lista-dto")
    public ResponseEntity<List<VentaDTO>> listarVentasConDetalle() {
        List<VentaDTO> ventas = ventaService.obtenerVentasConDetalle();
        return ResponseEntity.ok(ventas);
    }

}
