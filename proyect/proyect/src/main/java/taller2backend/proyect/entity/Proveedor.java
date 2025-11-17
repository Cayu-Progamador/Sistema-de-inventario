package taller2backend.proyect.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproveedor")
    private Long idProveedor;

    @Column(name = "nit")
    private String nit;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "correoelectronico")
    private String correoElectronico;


    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "proveedor")
    private Set<Pedido> pedidos;

    @OneToMany(mappedBy = "proveedor")
    private Set<EntregaProveedor> entregas;

    @OneToMany(mappedBy = "proveedor")
    private Set<ProductoProveedor>productoProveedor;
}
