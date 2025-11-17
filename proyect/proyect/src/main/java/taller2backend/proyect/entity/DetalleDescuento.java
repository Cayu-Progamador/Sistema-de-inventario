package taller2backend.proyect.entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detalledescuento")
public class DetalleDescuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idDetalleDescuento;

    // Atributo de la relación
    @Column(name = "montodescontado")
    private Double montoDescontado;

    // Relación N:1
    @ManyToOne
    @JoinColumn(name = "idventa")
    private Venta venta;

    // Relación N:1
    @ManyToOne
    @JoinColumn(name = "iddescuento")
    private Descuento descuento;
}
