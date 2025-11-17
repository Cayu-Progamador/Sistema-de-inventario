package taller2backend.proyect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productoproveedor")
public class ProductoProveedor {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idProductoProveedor; // ← Clave primaria obligatoria

    // Atributo de la relación
    @Column(name = "preciocompra")
    private Double precioCompra;

    // Relación N:1
    @ManyToOne
    @JoinColumn(name = "idproveedor")
    private Proveedor proveedor;

    // Relación N:1
    @ManyToOne
    @JoinColumn(name = "idproducto")
    private Producto producto;
}
