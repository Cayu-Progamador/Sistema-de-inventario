package taller2backend.proyect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import taller2backend.proyect.entity.MetodoPago;
import taller2backend.proyect.service.MetodoPagoService;

@RestController
@RequestMapping("/api/metod-opago")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;
    
    public MetodoPagoController(MetodoPagoService metodoPagoService) {
        this.metodoPagoService = metodoPagoService;
    }

    @GetMapping
    public ResponseEntity<List<MetodoPago>> getAllMetodoPago(){
        List<MetodoPago> metodos = metodoPagoService.listarMetodoPago();

        return ResponseEntity.ok(metodos);
    }
}
