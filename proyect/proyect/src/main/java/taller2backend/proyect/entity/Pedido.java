package taller2backend.proyect.entity;

import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpedido")
    private Long idPedido;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechapedido")
    private Date fechaPedido;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaentrega")
    private Date fechaEntrega;

    @Column(name = "totalpedido")
    private Double totalPedido;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "idproveedor")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "pedido" )
    private List<DetallePedido> detallePedidos = new ArrayList<>();
}
