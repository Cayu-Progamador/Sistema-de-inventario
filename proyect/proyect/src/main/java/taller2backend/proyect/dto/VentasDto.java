package taller2backend.proyect.dto;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class VentasDto {
    private Long id;
    private Date fecha;
    private Double total;
    private List<DetalleDto> detalles;
}
