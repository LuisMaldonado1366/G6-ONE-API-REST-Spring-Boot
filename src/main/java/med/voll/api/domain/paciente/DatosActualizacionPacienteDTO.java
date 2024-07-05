package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccionDTO;

public record DatosActualizacionPacienteDTO(
        @NotNull
        Long id,
        String nombre,
        String telefono,
        DatosDireccionDTO direccion) {
}
