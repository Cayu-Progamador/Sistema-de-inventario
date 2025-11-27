package taller2backend.proyect.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "venta")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idventa")
    private Long idVenta;

    @Column(name = "fechaventa")
    private Date fechaVenta;

    @Column(name = "numeroventa")
    private String numeroVenta;

    @Column(name = "totalventa")
    private Double  totalventa;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "totaldescuento")
    private Double totalDescuento;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "idempleado")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "idmetodopago")
    private MetodoPago metodoPago;

    @OneToOne(mappedBy = "venta", cascade = CascadeType.ALL)
   
    private DocumentoVenta documentoVenta;

    @OneToMany(mappedBy = "venta")
    private List<DetalleVenta> detalleVenta;

    @OneToMany(mappedBy = "venta")
    private Set<DetalleDescuento> detalleDescuento;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcliente")
    private Cliente cliente;
}
