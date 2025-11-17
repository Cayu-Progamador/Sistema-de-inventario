package taller2backend.proyect.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import taller2backend.proyect.entity.MetodoPago;
import taller2backend.proyect.repository.MetodoPagoRepository;
import taller2backend.proyect.service.MetodoPagoService;

@Service
public class MetodoPagoServiceImpl implements MetodoPagoService {
    
    private final MetodoPagoRepository metodoPagoRepository;
    
    public MetodoPagoServiceImpl(MetodoPagoRepository metodoPagoRepository) {
        this.metodoPagoRepository = metodoPagoRepository;
    }

    //llamar todos los metodos de pagos
    @Override
    public List<MetodoPago> listarMetodoPago() {
        return metodoPagoRepository.findAll();
    }
    
}
