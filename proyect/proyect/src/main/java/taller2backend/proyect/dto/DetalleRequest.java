package taller2backend.proyect.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleRequest {
    private Long productoId;
    private Integer cantidad;
}
