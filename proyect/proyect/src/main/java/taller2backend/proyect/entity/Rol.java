package taller2backend.proyect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrol")
    private Long idRol;

    @Column(name = "nombrerol")
    private String nombreRol;


    // Relación M:N sin aentontributos extra (Rol <-> Menu)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rolmenu",
        joinColumns = @JoinColumn(name = "idrol"),
        inverseJoinColumns = @JoinColumn(name = "idmenu")
    )
    private Set<Menu> menus;

    @ManyToMany(mappedBy = "roles")
    private Set<Usuario>usuarios;
}
