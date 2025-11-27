package taller2backend.proyect.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter
public class VentaResquestDTO {
    private List<DetalleRequest> detalles;
}
