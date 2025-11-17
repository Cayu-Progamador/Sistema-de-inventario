package taller2backend.proyect.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "idcliente")
    private Long idCliente;
    
    @Column(name= "nombre")
    private String nombre;

    @Column(name= "ci")
    private String ci;

    @Column(name= "correoelectronico")
    private String correoElectronico;

    @Column(name= "direccion")
    private String direccion;

    @Column(name= "telefono")
    private String telefono;

    
}
