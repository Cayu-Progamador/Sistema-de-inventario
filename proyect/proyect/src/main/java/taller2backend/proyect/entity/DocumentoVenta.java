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
@Table(name = "documentoventa")
public class DocumentoVenta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddocventa")
    private Long idDocumento;

    @Column(name = "tipodocumento")
    private String tipoDocumento;

    @Column(name = "numerodocumento")
    private String numeroDocumento;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaemision")
    private Date fechaEmision;
    
    @Column(name = "total")
    private Double total;

    @Column(name = "impuesto")
    private Double impuesto;
    
    // Relación 1:1
    @OneToOne
    @JoinColumn(name = "idventa")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Venta venta;
    
    // Relación N:1
    @ManyToOne
    @JoinColumn(name = "idcliente")
    private Cliente cliente;
}
