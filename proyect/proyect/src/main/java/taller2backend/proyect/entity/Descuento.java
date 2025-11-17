package taller2backend.proyect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import jakarta.persistence.Column;
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
@Table(name = "descuento")
public class Descuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddescuento")
    private Long idDescuento;

    @Column(name = "nombredescuento")
    private String nombreDescuento;

    @Column(name = "valor")
    private Double valor;

    @Column (name = "tipo")
    private String tipo;

    @Column(name = "activo")
    private Boolean activo;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechafin")
    private Date fechaFin;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechainicio")
    private Date fechaInicio;
}
