package taller2backend.proyect.entity;

import jakarta.persistence.Entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "promociones")
public class Promociones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpromociones")
    private Long idPromociones;

    @Column(name = "nombrepromociones")
    private String nombrePromociones;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechainicio")
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechafin")
    private Date fechaFin;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "valor")
    private Double valor ;


    @ManyToOne
    @JoinColumn(name = "idproducto")
    private Producto producto;
    
}
