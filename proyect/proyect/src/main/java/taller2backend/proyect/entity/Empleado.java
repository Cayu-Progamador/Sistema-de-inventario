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
@Table(name="empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Empleado {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name="idempleado")
    private Long idEmpleado;
    
    @Column(name= "ci")
    private String ci;
    
    @Column(name= "apellido")
    private String apellido;
    
    @Column(name= "nombre")
    private String nombre;
    
    @Column(name= "direccion")
    private String direccion;  
    
    @Column(name= "telefono")
    private String telefono;
    
    @Column(name= "correo")
    private String correo;
    
    @Column(name= "estado")
    private String estado;
    

}
