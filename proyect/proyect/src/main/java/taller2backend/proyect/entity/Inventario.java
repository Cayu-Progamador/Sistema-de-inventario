package taller2backend.proyect.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    @ToString.Exclude          // <--- AGREGA ESTO
    @EqualsAndHashCode.Exclude // <--- AGREGA ESTO
    private Producto producto;
    
}
