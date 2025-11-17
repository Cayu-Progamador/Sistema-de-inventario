package taller2backend.proyect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entregaproveedor")
public class EntregaProveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntrega;
     
    @Column(name = "estadoentrega")
    private String estadoEntrega;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaentrega")
    private Date fechaEntrega;

    @Column(name = "observaciones")
    private String observaciones;

    @OneToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;


    @ManyToOne
    @JoinColumn(name = "idproveedor")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "entregaProveedor")
    private Set<DetalleEntrega> detalleEntrega;
}
