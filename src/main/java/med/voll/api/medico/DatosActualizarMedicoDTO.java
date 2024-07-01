package med.voll.api.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.direccion.DatosDireccionDTO;

public record DatosActualizarMedicoDTO(
        @NotNull
        Long id,
        String nombre,
        String documento,
        DatosDireccionDTO direccion
) {
}
