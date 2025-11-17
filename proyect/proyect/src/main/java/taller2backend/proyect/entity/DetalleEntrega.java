package taller2backend.proyect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detalleentrega")
public class DetalleEntrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idDetalleEntrega; // ← Clave primaria obligatoria

    // Atributo de la relación (implícito en tu diagrama)
    @Column(name = "cantidadrecibida")
    private Integer cantidadRecibida;

    // Relación N:1
    @ManyToOne
    @JoinColumn(name = "identrega")
    private EntregaProveedor entregaProveedor;

    // Relación N:1
    @ManyToOne
    @JoinColumn(name = "idproducto")
    private Producto producto;
}
