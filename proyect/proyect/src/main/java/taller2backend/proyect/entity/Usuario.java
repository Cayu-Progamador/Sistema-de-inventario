

package taller2backend.proyect.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Long idUsuario;

    @Column(name = "nombreusuario")
    private String nombreUsuario;

    @Column(name = "password")
    private String password;

    // Relación 1:1
    @OneToOne
    @JoinColumn(name = "idempleado")
    private Empleado empleado;

    // Relación M:N sin atributos extra (Usuario <-> Rol)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuariorol",
        joinColumns = @JoinColumn(name = "idusuario"),
        inverseJoinColumns = @JoinColumn(name = "idrol")
    )
    private Set<Rol> roles;
}

