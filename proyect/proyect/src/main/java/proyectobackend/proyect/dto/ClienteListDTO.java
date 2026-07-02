package proyectobackend.proyect.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteListDTO{
    private Long idCliente; // <-- AÑADE ESTO (es crucial)

    private String nombre;

    private String ci;

    private String correoElectronico;

    private String direccion;
    
    private String telefono;
    
}
