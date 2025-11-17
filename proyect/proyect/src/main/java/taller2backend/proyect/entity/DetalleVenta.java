package taller2backend.proyect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalleventa")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idDetalleVenta; // ← Clave primaria obligatoria


    // Atributos de la relación
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "preciounitario")
    private Double precioUnitario; 

    // Relación N:1
    @ManyToOne
    @JoinColumn(name = "idventa")
    private Venta venta;

    // Relación N:1
    @ManyToOne
    @JoinColumn(name = "idproducto")
    private Producto producto;    
}
