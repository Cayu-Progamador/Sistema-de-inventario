package taller2backend.proyect.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {
    
    private String nombre;

    private String ci;

    private String correoElectronico;

   
    private String direccion;

    
    private String telefono;
}
