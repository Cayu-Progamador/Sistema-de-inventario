package taller2backend.proyect.entity;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproducto")
    private Long idProducto;

    @Column(name = "tipoproducto")
    private String tipoProducto;

    @Column(name = "codigobarra")
    private String codigoBarra;

    @Column(name = "codigoproducto")
    private String codigoProducto;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechavencimiento")
    private Date fechaVencimiento;

    @Column(name = "unidadmedida")
    private String unidadMedida;

    @Column(name = "nombreproducto")
    private String nombreProducto;

    @Column(name = "precio")
    private Double precio;

    @OneToOne(mappedBy = "producto" , cascade = CascadeType.ALL)
    private Inventario inventario;

    @OneToMany(mappedBy = "producto")
    private Set<Promociones> promociones;

    @OneToMany(mappedBy = "producto")
    private Set<DetalleVenta>detalleventa;

    @OneToMany(mappedBy = "producto")
    private Set<DetallePedido> detallePedido;

    @OneToMany(mappedBy = "producto")
    private Set<DetalleEntrega> detalleEntrega;

}
