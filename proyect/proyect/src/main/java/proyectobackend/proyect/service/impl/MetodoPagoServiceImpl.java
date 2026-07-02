package proyectobackend.proyect.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import proyectobackend.proyect.entity.MetodoPago;
import proyectobackend.proyect.repository.MetodoPagoRepository;
import proyectobackend.proyect.service.MetodoPagoService;

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
