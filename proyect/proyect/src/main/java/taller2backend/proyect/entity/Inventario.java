package taller2backend.proyect.entity;

import jakarta.persistence.Entity;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinventario")
    private Long idInventario;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaactualizacion")
    private Date fechaActualizacion;
    
    @Column(name = "stockminimo")
    private Integer stockMinimo;

    @Column(name = "stockmaximo")
    private Integer stockMaximo;

    @Column(name = "stockactual")
    private Integer stockActual;

    @Column(name = "ubicacion")
    private String ubicacion;
    
    // Relación 1:1
    @OneToOne
    @JoinColumn(name = "idproducto")
    private Producto producto;
    
}
