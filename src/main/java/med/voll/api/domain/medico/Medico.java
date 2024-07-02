package med.voll.api.domain.medico;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Table(name = "medicos")
@Entity(name = "medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;


    public Medico(DatosRegistroMedicoDTO datosRegistroMedicoDTO) {
        this.activo = true;
        this.nombre = datosRegistroMedicoDTO.nombre();
        this.email = datosRegistroMedicoDTO.email();
        this.telefono = datosRegistroMedicoDTO.telefono();
        this.documento = datosRegistroMedicoDTO.documento();
        this.especialidad = datosRegistroMedicoDTO.especialidad();
        this.direccion = new Direccion(datosRegistroMedicoDTO.direccion());
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumento() {
        return documento;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public Boolean getActivo() {
        return activo;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void actualizarDatos(DatosActualizarMedicoDTO datosActualizarMedicoDTO) {
        if (datosActualizarMedicoDTO.nombre() != null){
            this.nombre = datosActualizarMedicoDTO.nombre();
        }
        if (datosActualizarMedicoDTO.documento() != null){
            this.documento = datosActualizarMedicoDTO.documento();
        }
        if (datosActualizarMedicoDTO.direccion() != null){
            this.direccion = direccion.actualizarDireccion(datosActualizarMedicoDTO.direccion());
        }

    }

    public void desactivarMedico() {
        this.activo = false;
    }
}
